package fengkuang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author qyyzxty@icloud.com
 * 2021/5/15
 **/
public class KeyinTest {
    public static void main(String[] args) {
        try (
                //将System.in对象转换成Reader对象
                InputStreamReader reader = new InputStreamReader(System.in);
                //将普通的Reader包装成BufferedReader
                BufferedReader br = new BufferedReader(reader)
        ) {
            String line;
            //采用循环方式逐行读取
            while ( (line = br.readLine()) != null ) {
                //如果读取到的字符是"exit"
                if (line.equals("exit")) {
                    System.exit(1);
                }
                //打印读取内容
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
