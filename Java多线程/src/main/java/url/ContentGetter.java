package url;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author qyyzxty@icloud.com
 * 2021/4/15
 **/
public class ContentGetter {

    public static void main(String[] args) {
        try {
            URL url = new URL("https://www.baidu.com");
            Object o = url.getContent();
            System.out.println(o.getClass().getName());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
