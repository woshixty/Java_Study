package zip;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author qyyzxty@icloud.com
 * 2021/4/14
 **/
public class GZipAllFiles {

    //线程数固定为4
    public final static int THREAD_COUNT = 4;

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(THREAD_COUNT);
        List<String> strings = new ArrayList<>();
        strings.add("/Users/mr.stark/Desktop/1.jpg");
        strings.add("/Users/mr.stark/Desktop/2.jpg");
        strings.add("/Users/mr.stark/Desktop/3.jpg");
        strings.add("/Users/mr.stark/Desktop/4.jpg");
        for (String filename : strings) {
            File f = new File(filename);
            if (f.exists()) {
                if (f.isDirectory()) {
                    File[] files = f.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        if (!files[i].isDirectory()) {
                            //不递归处理目录
                            Runnable task = new GZipRunnable(files[i]);
                            pool.submit(task);
                        }
                    }
                } else {
                    Runnable task = new GZipRunnable(f);
                    pool.submit(task);
                }
            }
        }
        pool.shutdown();
    }
}
