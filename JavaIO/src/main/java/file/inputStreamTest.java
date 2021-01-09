package file;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/12/30
 **/
public class inputStreamTest {
    public static void main(String[] args) throws IOException {
        InputStream inputStream = new FileInputStream("src\\main\\resources\\test.txt");
        for ( ; ; ) {
            int n = inputStream.read();
            if (n==-1)
                break;
            System.out.println((char) n);
        }

        String s = readAsString(inputStream);
        inputStream.close();
    }

    private static String readAsString(InputStream inputStream) throws IOException {
        int n;
        StringBuilder sb = new StringBuilder();
        while ((n = inputStream.read()) != -1) {
            sb.append((char) n);
        }
        return sb.toString();
    }
}
