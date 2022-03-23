package net.qiujuer.lesson.sample.server;

import net.qiujuer.lesson.sample.foo.constants.UDPConstants;
import net.qiujuer.library.clink.utils.ByteUtils;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.util.UUID;

class UDPProvider {
    private static Provider PROVIDER_INSTANCE;

    static void start(int port) {
        stop();
        String sn = UUID.randomUUID().toString();
        Provider provider = new Provider(sn, port);
        provider.start();
        PROVIDER_INSTANCE = provider;
    }

    static void stop() {
        if (PROVIDER_INSTANCE != null) {
            PROVIDER_INSTANCE.exit();
            PROVIDER_INSTANCE = null;
        }
    }

    private static class Provider extends Thread {
        private final byte[] sn;
        private final int port;
        private boolean done = false;
        private DatagramSocket ds = null;
        // 存储消息的Buffer
        final byte[] buffer = new byte[128];

        Provider(String sn, int port) {
            super("Server-UDPProvider-Thread");
            this.sn = sn.getBytes();
            this.port = port;
        }

        @Override
        public void run() {
            super.run();
            System.out.println("UDPProvider Started.");
            try {
                // 监听20000 端口
                ds = new DatagramSocket(UDPConstants.PORT_SERVER);
                // 接收消息的Packet
                DatagramPacket receivePack = new DatagramPacket(buffer, buffer.length);
                while (!done) {
                    // 接收
                    ds.receive(receivePack);
                    // 打印接收到的信息与发送者的信息
                    // 发送者的IP地址
                    String clientIp = receivePack.getAddress().getHostAddress();
                    //发送者端口
                    int clientPort = receivePack.getPort();
                    //接收数据长度
                    int clientDataLen = receivePack.getLength();
                    //接收数据
                    byte[] clientData = receivePack.getData();
                    //报文合法性标志
                    boolean isValid = clientDataLen >= (UDPConstants.HEADER.length + 2 + 4)
                            && ByteUtils.startsWith(clientData, UDPConstants.HEADER);
                    //打印信息
                    System.out.println("UDPProvider receive form ip:" + clientIp
                            + "\tport:" + clientPort + "\tdataValid:" + isValid);
                    if (!isValid) {
                        // 无效继续
                        continue;
                    }
                    // 解析命令与回送端口
                    int index = UDPConstants.HEADER.length;
                    // https://blog.csdn.net/i6223671/article/details/88924481
                    // https://www.cnblogs.com/think-in-java/p/5527389.html
                    // 保证补码的一致性
                    // 获取命令
                    short cmd = (short) ((clientData[index++] << 8) | (clientData[index++] & 0xff));
                    // 获取回送端口
                    int responsePort = (((clientData[index++]) << 24) |
                            ((clientData[index++] & 0xff) << 16) |
                            ((clientData[index++] & 0xff) << 8) |
                            ((clientData[index] & 0xff)));
                    // 判断合法性
                    if (cmd == 1 && responsePort > 0) {
                        // 构建一份回送数据
                        // 将byte数组转换为Buffer
                        ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
                        // 放入验证头
                        byteBuffer.put(UDPConstants.HEADER);
                        // 放入cmd
                        byteBuffer.putShort((short) 2);
                        byteBuffer.putInt(port);
                        byteBuffer.put(sn);
                        int len = byteBuffer.position();
                        // 直接根据发送者构建一份回送信息
                        DatagramPacket responsePacket = new DatagramPacket(buffer, len,
                                receivePack.getAddress(), responsePort);
                        ds.send(responsePacket);
                        System.out.println("UDPProvider response to:" + clientIp +
                                "\tport:" + responsePort + "\tdataLen:" + len);
                    } else {
                        System.out.println("UDPProvider receive cmd nonsupport; cmd:" + cmd + "\tport:" + port);
                    }
                }
            } catch (Exception ignored) {
            } finally {
                close();
            }
            // 完成
            System.out.println("UDPProvider Finished!");
        }

        private void close() {
            if (ds != null) {
                ds.close();
                ds = null;
            }
        }

        /**
         * 提供结束
         */
        void exit() {
            done = true;
            close();
        }
    }
}
