package net.qiujuer.library.clink.impl;

import net.qiujuer.library.clink.core.IoProvider;
import net.qiujuer.library.clink.utils.CloseUtils;


import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class IoSelectorProvider implements IoProvider {
    // 是否关闭标志，默认false
    private final AtomicBoolean isClosed = new AtomicBoolean(false);

    // 是否处于某个过程，默认为false
    private final AtomicBoolean inRegInput = new AtomicBoolean(false);
    private final AtomicBoolean inRegOutput = new AtomicBoolean(false);

    // 读和写的多路复用器
    private final Selector readSelector;
    private final Selector writeSelector;

    // 注册令牌
    private final HashMap<SelectionKey, Runnable> inputCallbackMap = new HashMap<>();
    private final HashMap<SelectionKey, Runnable> outputCallbackMap = new HashMap<>();

    // 输入输出线程池
    private final ExecutorService inputHandlePool;
    private final ExecutorService outputHandlePool;

    // 构造函数
    public IoSelectorProvider() throws IOException {
        // 打开读和写选择器
        readSelector = Selector.open();
        writeSelector = Selector.open();

        // 创建输入输出线程池
        inputHandlePool = Executors.newFixedThreadPool(4,
                new IoProviderThreadFactory("IoProvider-Input-Thread-"));
        outputHandlePool = Executors.newFixedThreadPool(4,
                new IoProviderThreadFactory("IoProvider-Output-Thread-"));

        // 开始输出输入的监听，分别为其启动一个线程
        startRead();
        startWrite();
    }

    // 开始读
    private void startRead() {
        Thread thread = new Thread("Clink IoSelectorProvider ReadSelector Thread") {

            @Override
            public void run() {
                while (!isClosed.get()) {
                    try {
                        // 没有可读事件就阻塞
                        if (readSelector.select() == 0) {
                            // 等待输入信号变为 真
                            waitSelection(inRegInput);
                            continue;
                        }

                        // 获取注册令牌set并遍历
                        Set<SelectionKey> selectionKeys = readSelector.selectedKeys();
                        for (SelectionKey selectionKey : selectionKeys) {
                            if (selectionKey.isValid()) {
                                handleSelection(selectionKey, SelectionKey.OP_READ, inputCallbackMap, inputHandlePool);
                            }
                        }
                        // 清除令牌
                        selectionKeys.clear();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
    }

    // 开始写
    private void startWrite() {
        Thread thread = new Thread("Clink IoSelectorProvider WriteSelector Thread") {
            @Override
            public void run() {
                while (!isClosed.get()) {
                    try {
                        if (writeSelector.select() == 0) {
                            waitSelection(inRegOutput);
                            continue;
                        }
                        // 获取访问”已选择键集“中的就绪通道
                        // selectedKeys 内部包含了所有发生的事件
                        Set<SelectionKey> selectionKeys = writeSelector.selectedKeys();
                        for (SelectionKey selectionKey : selectionKeys) {
                            if (selectionKey.isValid()) {
                                handleSelection(selectionKey, SelectionKey.OP_WRITE, outputCallbackMap, outputHandlePool);
                            }
                        }
                        selectionKeys.clear();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
    }

    // 注册可读事件
    @Override
    public boolean registerInput(SocketChannel channel, HandleInputCallback callback) {
        return registerSelection(channel, readSelector, SelectionKey.OP_READ, inRegInput,
                inputCallbackMap, callback) != null;
    }

    // 注册可写事件
    @Override
    public boolean registerOutput(SocketChannel channel, HandleOutputCallback callback) {
        return registerSelection(channel, writeSelector, SelectionKey.OP_WRITE, inRegOutput,
                outputCallbackMap, callback) != null;
    }

    // 取消注册可读事件
    @Override
    public void unRegisterInput(SocketChannel channel) {
        unRegisterSelection(channel, readSelector, inputCallbackMap);
    }

    // 取消注册可写事件
    @Override
    public void unRegisterOutput(SocketChannel channel) {
        unRegisterSelection(channel, writeSelector, outputCallbackMap);
    }

    // 关闭
    @Override
    public void close() {
        if (isClosed.compareAndSet(false, true)) {
            inputHandlePool.shutdown();
            outputHandlePool.shutdown();

            inputCallbackMap.clear();
            outputCallbackMap.clear();

            readSelector.wakeup();
            writeSelector.wakeup();

            CloseUtils.close(readSelector, writeSelector);
        }
    }

    // 等待事件发生
    private static void waitSelection(final AtomicBoolean locker) {
        //noinspection SynchronizationOnLocalVariableOrMethodParameter
        synchronized (locker) {
            if (locker.get()) {
                try {
                    locker.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 注册
     * @param channel
     * @param selector
     * @param registerOps  兴趣事件
     * @param locker  可读标志位
     * @param map
     * @param runnable
     * @return
     */
    private static SelectionKey registerSelection(SocketChannel channel, Selector selector,
                                                  int registerOps, AtomicBoolean locker,
                                                  HashMap<SelectionKey, Runnable> map,
                                                  Runnable runnable) {

        //noinspection SynchronizationOnLocalVariableOrMethodParameter
        synchronized (locker) {
            // 设置锁定状态
            locker.set(true);

            try {
                // 唤醒当前的selector，让selector不处于select()状态
                selector.wakeup();

                SelectionKey key = null;
                if (channel.isRegistered()) {
                    // 查询是否已经注册过
                    key = channel.keyFor(selector);
                    if (key != null) {
                        key.interestOps(key.readyOps() | registerOps);
                    }
                }

                if (key == null) {
                    // 注册selector得到Key
                    key = channel.register(selector, registerOps);
                    // 注册回调
                    map.put(key, runnable);
                }

                return key;
            } catch (ClosedChannelException e) {
                return null;
            } finally {
                // 解除锁定状态
                locker.set(false);
                try {
                    // 通知
                    locker.notify();
                } catch (Exception ignored) {
                }
            }
        }
    }

    /**
     * 取消注册
     * @param channel
     * @param selector
     * @param map
     */
    private static void unRegisterSelection(SocketChannel channel, Selector selector,
                                            Map<SelectionKey, Runnable> map) {
        if (channel.isRegistered()) {
            SelectionKey key = channel.keyFor(selector);
            if (key != null) {
                // 取消监听的方法
                key.cancel();
                map.remove(key);
                // TODO: 2022/3/5 为什么要唤醒阻塞事件
                selector.wakeup();
            }
        }
    }

    private static void handleSelection(SelectionKey key, int keyOps,
                                        HashMap<SelectionKey, Runnable> map,
                                        ExecutorService pool) {
        // 重点
        // 取消继续对keyOps的监听
        // TODO: 2022/3/5 未看懂
        key.interestOps(key.readyOps() & ~keyOps);

        // 通过key获取任务
        Runnable runnable = null;
        try {
            runnable = map.get(key);
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }

        if (runnable != null && !pool.isShutdown()) {
            // 异步调度
            pool.execute(runnable);
        }
    }

    static class IoProviderThreadFactory implements ThreadFactory {
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        IoProviderThreadFactory(String namePrefix) {
            SecurityManager s = System.getSecurityManager();
            this.group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            this.namePrefix = namePrefix;
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
}
