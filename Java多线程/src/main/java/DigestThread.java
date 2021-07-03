import javax.xml.bind.DatatypeConverter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author qyyzxty@icloud.com
 * @data 2021/4/14
 **/
public class DigestThread extends Thread {

    private String fileName;

    public DigestThread( String fileName ) {
        this.fileName = fileName;
    }

    @Override
    public void run() {
        try {
            FileInputStream in = new FileInputStream(fileName);
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            //读取文件
            DigestInputStream din = new DigestInputStream(in, sha);
            while (din.read() != -1) ;
            din.close();
            byte[] digest = sha.digest();

            StringBuilder result = new StringBuilder(fileName);
            result.append(": ");
            result.append(DatatypeConverter.printHexBinary(digest));
            System.out.println(result);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //针对每个文件名启动一个新的进程：DigestThread
    public static void main(String[] args) {
        for (String fileName : args) {
            Thread t = new DigestThread(fileName);
            t.start();
        }
//        Thread t = new DigestThread("C:\\Users\\qyyzx\\Desktop\\hello.html");
//        t.start();
    }
}