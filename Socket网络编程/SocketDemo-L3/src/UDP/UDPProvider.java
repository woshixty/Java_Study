package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.PortUnreachableException;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

/**
 * @author qyyzxty@icloud.com
 * 2021/7/20
 * UDP提供者，用于提供服务
 **/
public class UDPProvider {
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
}
