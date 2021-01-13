package file;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import static java.nio.charset.StandardCharsets.*;

/**
 * @author qyyzxty@icloud.com
 * @data 2021/1/13
 * reader
 * Reader是Java的IO库提供的另一个输入流接口
 * 和InputStream的区别是:
 * InputStream是一个字节流，即以byte为单位读取，而Reader是一个字符流，即以char为单位读取
 **/
public class g_readTest {
    public static void main(String[] args) throws IOException {
        /**
         * FileReader
         * FileReader是Reader的一个子类，它可以打开文件并获取Reader
         * 下面的代码演示了如何完整地读取一个FileReader的所有字符
         */
        Reader reader = new FileReader("src\\main\\resources\\test.txt");  // 创建一个FileReader对象
        for ( ; ; ) {
            int n = reader.read();  //反复调用直到返回-1
            if (n == -1)
                break;
            System.out.println((char) n);  //打印char
        }
        reader.close();  //关闭流

        // 要避免乱码问题，我们需要在创建FileReader时指定编码
//        Reader reader1 = new FileReader("src\\main\\resources\\test.txt", UTF_8);

    }
}
