import java.io.*;
import java.net.*;

/**
 * @author qyyzxty@icloud.com
 * 2021/7/18
 * 客户端
 **/
public class Client {
    private static final int PORT = 20000;
    private static final int LOCAL_PORT = 20001;

    /*
    老的代码
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        //设置超时时间
        socket.setSoTimeout(3000);
        //设置通信地址
        socket.connect(new InetSocketAddress(Inet4Address.getLocalHost(), 2000), 3000);
        System.out.println("已发起服务器连接");
        System.out.println("客户端信息：" + socket.getLocalAddress() + "P：" + socket.getLocalPort());
        System.out.println("服务器信息：" + socket.getInetAddress() + "P：" + socket.getPort());
        try {
            todo(socket);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("异常关闭");
        }
        socket.close();
        System.out.println("客户端退出");
    }
    */

    private static void todo(Socket client) throws IOException {
        //构建键盘输入流
        InputStream in = System.in;
        BufferedReader input = new BufferedReader(new InputStreamReader(in));
        //得到Socket输出流，并转换为打印流
        OutputStream outputStream = client.getOutputStream();
        PrintStream socketPrintStream = new PrintStream(outputStream);
        //得到Socket输入流，并转换为BufferedReader
        InputStream inputStream = client.getInputStream();
        BufferedReader socketBufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        boolean flag = true;
        do {
            //键盘读取一行
            String str = input.readLine();
            //发送到服务器
            socketPrintStream.println(str);
            socketPrintStream.flush();
            //从服务器读取一行
            String echo = socketBufferedReader.readLine();
            if ("bye".equalsIgnoreCase(echo)) {
                flag = false;
            } else {
                System.out.println(echo);
            }
        } while (flag);
        //资源释放
        socketPrintStream.close();
        socketBufferedReader.close();
    }

    public static void main(String[] args) {

    }

    private static Socket createSocket() throws IOException {
        /*
        这种方式不推荐
        //无代理模式，等效于空构造函数
        Socket socket = new Socket(Proxy.NO_PROXY);
        //新建一份具有HTTP代理的套接字，传输数据将通过www.baidu.com:8080端口转发
        Proxy proxy = new Proxy(Proxy.Type.HTTP,
                new InetSocketAddress(Inet4Address.getByName("www.baidu.com"), 8800));
        //新建一个套接字，并且直接连接到本地20000的服务器上，并且绑定本地20001端口上
        socket = new Socket("localhost", PORT);
        socket = new Socket(Inet4Address.getLocalHost(), PORT, Inet4Address.getLocalHost(), LOCAL_PORT);
        */
        Socket socket = new Socket();
        //绑定本地20001端口
        socket.bind(new InetSocketAddress(Inet4Address.getLocalHost(), LOCAL_PORT));
        return socket;
    }

    private static void initSocket(Socket socket) throws SocketException {
        //设置读取超时时间为2秒
        socket.setSoTimeout(3000);
        //是否复用未完全关闭的Socket地址，对于指定bind操作后的套接字有效
        socket.setReuseAddress(true);
        //是否开启Nagle算法（是否立即发送数据）
//        socket.setTcpNoDelay(false);
        //是否需要在长时间无数据响应时发送确认数据（类似心跳包），时间大约为2小时
        socket.setKeepAlive(true);
        //对于close关闭操作行为进行怎样的处理；默认为false，0
        //false、0：默认情况，关闭时立即返回，底层系统接管输出流，将缓冲区内的数据发送完成
        //true、0：关闭时立即返回，缓冲区数据抛弃，直接发送RST结束命令到对方，并无需经过2MSL等待
        //true、200：关闭时最长阻塞200毫秒，随后按第二情况处理
        socket.setSoLinger(true, 20);
        //是否让紧急数据内敛，默认false紧急数据通过 socket.sendUrgentData(1);发送
        socket.setOOBInline(true);
        //设置接收发送缓冲器大小
        socket.setReceiveBufferSize(64*1024*1024);
        socket.setSendBufferSize(64*1024*1024);
        //设置性能参数：短链接，延迟，带宽的相对重要性
        socket.setPerformancePreferences(1, 1, 1);
    }
}
