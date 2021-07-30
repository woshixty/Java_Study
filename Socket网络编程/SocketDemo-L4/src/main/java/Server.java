import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author qyyzxty@icloud.com
 * 2021/7/18
 * 服务器端
 **/
public class Server {
    private static final int PORT = 20000;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = createServerSocket();
        initServerSocket(serverSocket);
        //绑定到本地端口
        serverSocket.bind(new InetSocketAddress(Inet4Address.getLocalHost(), PORT), 50);
        System.out.println("服务器已经准备就绪");
        System.out.println("服务器信息：" + serverSocket.getInetAddress() + "P：" + serverSocket.getLocalPort());
        //等待客户端连接
        while (true) {
            //得到客户端
            Socket client = serverSocket.accept();
            //客户端启动异步线程
            ClientHandler clientHandler = new ClientHandler(client);
            //启动线程
            clientHandler.start();
        }
    }

    private static ServerSocket createServerSocket() throws IOException {
        //创建基础的ServerSocket
        ServerSocket serverSocket = new ServerSocket();
        /*
        绑定到本地端口20000上，并且设置当前可允许等待链接的队列为50个
        serverSocket = new ServerSocket(PORT);
        等效于上面的方案，队列设置为50个
        serverSocket = new ServerSocket(PORT, 50);
        与上面等同
        serverSocket = new ServerSocket(PORT, 50, Inet4Address.getLocalHost());
         */
        return serverSocket;
    }

    private static void initServerSocket(ServerSocket serverSocket) throws IOException {
        //是否复用未完全关闭的地址端口
        serverSocket.setReuseAddress(true);
        //等效Socket#setReceiveBufferSize
        serverSocket.setReceiveBufferSize(64 * 1024 * 1024);
        //设置serverSocket#accept超时时间
        //serverSocket.setSoTimeout(2000);
        //设置性能参数：短链接，延迟，带宽的相对重要性
        serverSocket.setPerformancePreferences(1, 1, 1);
    }

    /**
     * 客户端消息处理
     */
    private static class ClientHandler extends Thread {
        private Socket socket;
        private boolean flag = true;
        ClientHandler(Socket socket) {
            this.socket = socket;
        }
        @Override
        public void run() {
            super.run();
            System.out.println("新的客户端连接：" + socket.getInetAddress() + "P：" + socket.getPort());
            try {
                //得到打印流，用于数据输出；服务器回送数据使用
                PrintStream socketOutPut = new PrintStream(socket.getOutputStream());
                //得到输入流，用于接收数据
                BufferedReader socketInput = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()
                ));
                do {
                    String str = socketInput.readLine();
                    if ("bye".equalsIgnoreCase(str)) {
                        flag = false;
                        //回送数据
                        socketOutPut.println("bye");
                        socketOutPut.flush();
                    } else {
                        //回送数据
                        System.out.println(str);
                        socketOutPut.println("回送：" + str.length());
                        socketOutPut.flush();
                    }
                }while (flag);
                socketInput.close();
                socketOutPut.close();
            } catch (IOException e) {
                System.out.println("连接异常断开");
                e.printStackTrace();
            } finally {
                //连接关闭
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("客户端已关闭" + socket.getInetAddress() + "P：" + socket.getPort());
        }
    }
}
