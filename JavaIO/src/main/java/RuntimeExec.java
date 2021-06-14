
import java.io.IOException;
import java.io.InputStream;

/**
 * @author qyyzxty@icloud.com
 * 2021/5/16
 **/
public class RuntimeExec {
    public static void main(String[] args) throws IOException {
        Process process = Runtime.getRuntime().exec("open /Users/mr.stark/Desktop/test.java");
        InputStream inputStream = process.getInputStream();
        process.getErrorStream();
        process.getOutputStream();
    }
}
