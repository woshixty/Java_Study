package UDP;

import UDPEXPER.MessageCreator;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @author qyyzxty@icloud.com
 * 2021/7/20
 * UDP提供者，用于提供服务
 **/
public class UDPProvider {
    /** 第一次写的时候
    public static void main(String[] args) throws IOException {
        System.out.println("UAPProvider Started");
        //作为接收者，指定一个端口用于数据接收
        DatagramSocket ds = new DatagramSocket(20000);
        //构建接收实体
        final byte[] buf = new byte[512];
        DatagramPacket receivePack = new DatagramPacket(buf, buf.length);
        //接收
        ds.receive(receivePack);
        //打印接收到的信息与发送者的信息
        //发送者IP地址
        String ip = receivePack.getAddress().getHostAddress();
        int port = receivePack.getPort();
        int dataLen = receivePack.getLength();
        String data = new String(receivePack.getData(), 0, dataLen);
        System.out.println("UDPProvider receive from ip：" + ip
        + "\tport" + port + "\tdata：" + data);
        //构建一份回送数据
        String responseData = "Receive data with len" + dataLen;
        byte[] responseDataBytes = responseData.getBytes(StandardCharsets.UTF_8);
        //直接根据发送者信息创建一份回送信息
        DatagramPacket responsePacket = new DatagramPacket(responseDataBytes,
                responseDataBytes.length,
                receivePack.getAddress(),
                receivePack.getPort());
        ds.send(responsePacket);
        //完成
        System.out.println("UDPProvider Finished");
        ds.close();
    }
    */
    public static void main(String[] args) throws IOException {
        //生成一份唯一标识
        String sn = UUID.randomUUID().toString();
        Provider provider = new Provider(sn);
        provider.start();
        //读取键盘任意信息后退出
        System.in.read();
        provider.exit();
    }

    private static class Provider extends Thread {
        private final String sn;
        private boolean done = false;
        private DatagramSocket ds = null;

        private Provider(String sn) {
            this.sn = sn;
        }

        @Override
        public void run() {
            super.run();
            System.out.println("UAPProvider Started");
            try {
                //作为接收者，指定一个端口用于数据接收
                ds = new DatagramSocket(20000);
                while (!done) {
                    //构建接收实体
                    final byte[] buf = new byte[512];
                    DatagramPacket receivePack = new DatagramPacket(buf, buf.length);
                    //接收
                    ds.receive(receivePack);
                    //打印接收到的信息与发送者的信息
                    //发送者IP地址
                    String ip = receivePack.getAddress().getHostAddress();
                    int port = receivePack.getPort();
                    int dataLen = receivePack.getLength();
                    String data = new String(receivePack.getData(), 0, dataLen);
                    System.out.println("UDPProvider receive from ip：" + ip
                            + "\tport" + port + "\tdata：" + data);
                    //解析端口号
                    int responsePort = MessageCreator.parsePort(data);
                    if (responsePort != -1) {
                        //构建一份回送数据
                        String responseData = MessageCreator.buildWithSn(sn);
                        byte[] responseDataBytes = responseData.getBytes(StandardCharsets.UTF_8);
                        //直接根据发送者信息创建一份回送信息
                        DatagramPacket responsePacket = new DatagramPacket(responseDataBytes,
                                responseDataBytes.length,
                                receivePack.getAddress(),
                                receivePack.getPort());
                        ds.send(responsePacket);
                    }
                }
                //完成
                System.out.println("UDPProvider Finished");
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                ds.close();
            }
        }

        private void close() {
            if (ds != null) {
                ds.close();
                ds = null;
            }
        }

        private void exit() {
            done = true;
        }
    }
}
