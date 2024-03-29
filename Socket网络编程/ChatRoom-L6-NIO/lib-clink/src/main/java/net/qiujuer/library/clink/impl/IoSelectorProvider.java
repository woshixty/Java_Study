package net.qiujuer.library.clink.impl;

import com.sun.org.apache.bcel.internal.generic.Select;
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
    private final AtomicBoolean isClosed = new AtomicBoolean(false);

    //是否处于注册Input过程中
    private final AtomicBoolean inRegInput = new AtomicBoolean(false);
    //是否处于注册Output过程中
    private final AtomicBoolean inRegOutput = new AtomicBoolean(false);

    //读和写分开
    private final Selector readSelector;
    private final Selector writeSelector;

    //输入输出callback
    private final HashMap<SelectionKey, Runnable> inputCallbackMap = new HashMap<>();
    private final HashMap<SelectionKey, Runnable> outputCallbackMap = new HashMap<>();

    //读和写的线程池分开
    private final ExecutorService inputHandlePool;
    private final ExecutorService outputHandlePool;

    private SocketChannel channel;
    private Selector selector;
    private int registerOps;
    private AtomicBoolean locker;
    private HashMap<SelectionKey, Runnable> map;
    private Runnable runnable;

    public IoSelectorProvider() throws IOException {
        readSelector = Selector.open();
        writeSelector = Selector.open();
        inputHandlePool = Executors.newFixedThreadPool(4,
                new IoProviderThreadFactory("IoProvider-Input-Thread-"));
        outputHandlePool = Executors.newFixedThreadPool(4,
                new IoProviderThreadFactory("IoProvider-Output-Thread-"));
        //开始输出输入的监听
        startRead();
        startWrite();
    }

    @Override
    public boolean registerInput(SocketChannel channel, HandleInputCallback callback) {
        return registerSelection(channel, readSelector, SelectionKey.OP_READ, inRegInput, inputCallbackMap, callback) != null;
    }

    @Override
    public boolean registerOutput(SocketChannel channel, HandleOutputCallback callback) {
        return registerSelection(channel, writeSelector, SelectionKey.OP_WRITE, inRegOutput, outputCallbackMap, callback) != null;
    }

    //解除注册
    @Override
    public void unRegisterInput(SocketChannel channel) {
        unRegisterSelection(channel, readSelector, inputCallbackMap);
    }

    @Override
    public void unRegisterOutput(SocketChannel channel) {
        unRegisterSelection(channel, writeSelector, outputCallbackMap);
    }

    @Override
    public void close() throws IOException {
        if (isClosed.compareAndSet(false, true)) {
            //关闭线程池
            inputHandlePool.shutdown();
            outputHandlePool.shutdown();
            //清空map
            inputCallbackMap.clear();
            outputCallbackMap.clear();
            //唤醒selector
            readSelector.wakeup();
            writeSelector.wakeup();
            //关闭
            CloseUtils.close(readSelector, writeSelector);
        }
    }

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

    private static void handleSelection(SelectionKey key, int keyOps,
                                        HashMap<SelectionKey, Runnable> map,
                                        ExecutorService pool) {
        //重点
        //取消继续对keyOps的监听
        key.interestOps(key.readyOps() & ~keyOps);
        Runnable runnable = null;
        try {
            runnable = map.get(key);
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
        if (runnable != null && !pool.isShutdown()) {
            //异步调度
            pool.execute(runnable);
        }
    }

    //读取的线程
    private void startRead() {
        Thread thread = new Thread("Clink IoSelectorProvider ReadSelector Thread") {
            @Override
            public void run() {
                while (!isClosed.get()) {
                    try {
                        if (readSelector.select() == 0) {
                            //说明没有数据
                            waitSelection(inRegInput);
                            continue;
                        }
                        //拿到所有的key
                        Set<SelectionKey> selectionKeys = readSelector.selectedKeys();
                        for (SelectionKey selectionKey : selectionKeys) {
                            if (selectionKey.isValid()) {
                                //处理具体的每一个Selector，进行读
                                handleSelection(selectionKey, SelectionKey.OP_READ, inputCallbackMap, inputHandlePool);
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

    private static SelectionKey registerSelection(SocketChannel channel, Selector selector, int registerOps,
                                                 AtomicBoolean locker, HashMap<SelectionKey, Runnable> map,
                                                 Runnable runnable) {
        synchronized (locker) {
            //设置锁定状态
            locker.set(true);
            try {
                //喚醒当前selector，让selector不处于select()状态
                selector.wakeup();
                SelectionKey key = null;
                //查询是否已经注册过
                if (channel.isRegistered()) {
                    key = channel.keyFor(selector);
                    if (key != null) {
                        key.interestOps(key.readyOps() | registerOps);
                    }
                }
                if (key == null) {
                    //注册selector得到key
                    key = channel.register(selector, registerOps);
                    //注册回调
                    map.put(key, runnable);
                }
                return key;
            } catch (ClosedChannelException e) {
                return null;
            } finally {
                //解除锁定状态
                locker.set(false);
                try {
                    //通知
                    locker.notify();
                } catch (Exception e) {

                }
            }
        }
    }

    //解除注册
    private static void unRegisterSelection(SocketChannel channel, Selector selector, Map<SelectionKey, Runnable> map) {
        //是注册的状态
        if (channel.isRegistered()) {
            SelectionKey key = channel.keyFor(selector);
            if (key != null) {
                //取消监听
                key.cancel();
                map.remove(key);
                selector.wakeup();
            }
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
