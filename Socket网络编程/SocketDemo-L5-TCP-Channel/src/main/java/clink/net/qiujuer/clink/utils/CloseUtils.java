package clink.net.qiujuer.clink.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author qyyzxty@icloud.com
 * @data 2021/8/2
 **/
public class CloseUtils {
    public static void close(Closeable...closeables) {
        if (closeables == null) {
            return;
        }
        for (Closeable closeable : closeables) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
