package internaeAdd;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author qyyzxty@icloud.com
 * 2021/4/14
 **/
public class BaiDuByName {

    public static void main(String[] args) {
        try {
            //联网跑程序
            //域名获取ip
//            InetAddress address = InetAddress.getByName("www.baidu.com");
//            System.out.println(address);
            //ip获取域名
//            InetAddress address = InetAddress.getByName("10.3.48.54");
//            System.out.println(address);
            //返回本机IP
//            InetAddress address = InetAddress.getLocalHost();
//            System.out.println(address);
            //给定地址，找出主机名
            InetAddress ia = InetAddress.getByName("47.107.73.216");
            System.out.println(ia.getCanonicalHostName());
        } catch (UnknownHostException ex) {
            System.out.println("could not find www.baidu.com");
        }
    }

}
