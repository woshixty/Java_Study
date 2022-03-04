import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author qyyzxty@icloud.com
 * 2021/7/18
 * 服务器端
 **/
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(2000);
        System.out.println("服务器已经准备就绪");
        System.out.println("服务器信息：" + server.getInetAddress() + "P：" + server.getLocalPort());
        //等待客户端连接
        while (true) {
            //得到客户端
            Socket client = server.accept();
            //客户端启动异步线程
            ClientHandler clientHandler = new ClientHandler(client);
            //启动线程
            clientHandler.start();
        }
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
                    } else {
                        //回送数据
                        System.out.println(str);
                        socketOutPut.println("回送：" + str.length());
                    }
                    socketOutPut.flush();
                } while (flag);
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
