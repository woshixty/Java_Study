package net.qiujuer.lesson.sample.client;

import net.qiujuer.lesson.sample.client.bean.ServerInfo;
import net.qiujuer.library.clink.core.Connector;
import net.qiujuer.library.clink.utils.CloseUtils;
import java.io.*;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;

public class TCPClient extends Connector {
//    //连接
//    private final Socket socket;
//    //读取
//    private final ReadHandler readHandler;
//    //输出
//    private final PrintStream printStream;

    public TCPClient(SocketChannel socketChannel) throws IOException {
//        this.socket = socket;
//        this.readHandler = readHandler;
//        printStream = new PrintStream(socket.getOutputStream());
        setup(socketChannel);
    }

    public void exit() {
//        readHandler.exit();
//        CloseUtils.close(printStream);
        CloseUtils.close(this);
    }

//    public void send(String msg) {
//        printStream.println(msg);
//    }

    @Override
    public void onChannelClosed(SocketChannel channel) {
        super.onChannelClosed(channel);
        System.out.println("连接已关闭，无法读取数据");
    }

    public static TCPClient startWith(ServerInfo info) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        // 超时时间
//        socket.setSoTimeout(3000);

        // 连接本地，端口2000；超时时间3000ms
        socketChannel.connect(new InetSocketAddress(Inet4Address.getByName(info.getAddress()), info.getPort()));

        System.out.println("已发起服务器连接，并进入后续流程～");
        System.out.println("客户端信息：" + socketChannel.getLocalAddress());
        System.out.println("服务器信息：" + socketChannel.getRemoteAddress());

        try {
//            ReadHandler readHandler = new ReadHandler(socket.getInputStream());
//            readHandler.start();
            return new TCPClient(socketChannel);
        } catch (Exception e) {
            System.out.println("连接异常");
            CloseUtils.close(socketChannel);
        }
        return null;
    }

    private static void write(Socket client) throws IOException {
        // 构建键盘输入流
        InputStream in = System.in;
        BufferedReader input = new BufferedReader(new InputStreamReader(in));

        // 得到Socket输出流，并转换为打印流
        OutputStream outputStream = client.getOutputStream();
        PrintStream socketPrintStream = new PrintStream(outputStream);

        do {
            // 键盘读取一行
            String str = input.readLine();
            // 发送到服务器
            socketPrintStream.println(str);

            if ("00bye00".equalsIgnoreCase(str)) {
                break;
            }
        } while (true);

        // 资源释放
        socketPrintStream.close();
    }

//    static class ReadHandler extends Thread {
//        private boolean done = false;
//        private final InputStream inputStream;
//
//        ReadHandler(InputStream inputStream) {
//            this.inputStream = inputStream;
//        }
//
//        @Override
//        public void run() {
//            super.run();
//            try {
//                // 得到输入流，用于接收数据
//                BufferedReader socketInput = new BufferedReader(new InputStreamReader(inputStream));
//                do {
//                    String str;
//                    try {
//                        // 客户端拿到一条数据
//                        str = socketInput.readLine();
//                    } catch (SocketTimeoutException e) {
//                        continue;
//                    }
//                    if (str == null) {
//                        System.out.println("连接已关闭，无法读取数据！");
//                        break;
//                    }
//                    // 打印到屏幕
//                    System.out.println(str);
//                } while (!done);
//            } catch (Exception e) {
//                if (!done) {
//                    System.out.println("连接异常断开：" + e.getMessage());
//                }
//            } finally {
//                // 连接关闭
//                CloseUtils.close(inputStream);
//            }
//        }
//
//        void exit() {
//            done = true;
//            CloseUtils.close(inputStream);
//        }
//    }
}
