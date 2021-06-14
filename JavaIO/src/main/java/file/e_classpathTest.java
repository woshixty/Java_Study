package file;

import java.io.*;

/**
 * @author qyyzxty@icloud.com
 * @data 2021/1/13
 **/

public class e_classpathTest {

    private void test() {
        try(InputStream input = this.getClass().getResourceAsStream("/default.properties")) {
            for ( ; ; ) {
                int n = input.read();
                if (n==-1)
                    break;
                System.out.println ((char) n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void hello() throws FileNotFoundException {
        // 很多Java程序启动的时候，都需要读取配置文件
        // 例如，从一个.properties文件中读取配置：
        String conf = new String("src\\main\\resources\\default.properties");
        try(InputStream input = new FileInputStream(conf)) {
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * 这段代码要正常执行，必须在目录里创建default.properties文件
         * 但是，在Linux系统上，路径和Windows的又不一样
         * 从classpath读取文件就可以避免不同环境下文件路径不一致的问题：
         * 如果我们把default.properties文件放到classpath中，就不用关心它的实际存放路径
         */
        test();
    }
}
