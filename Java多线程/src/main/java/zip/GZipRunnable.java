package zip;

import java.io.*;
import java.util.zip.GZIPOutputStream;

/**
 * @author qyyzxty@icloud.com
 * 2021/4/14
 **/
public class GZipRunnable implements Runnable {

    private final File input;

    public GZipRunnable(File input) {
        this.input = input;
    }

    public void run() {
        //不压缩已经压缩的文件
        if(!input.getName().endsWith(".gz")) {
            File output = new File(input.getParent(), input.getName() + ".gz");
            if (!output.exists()) {
                //文件不存在
                try (  //java7专有
                       InputStream in = new BufferedInputStream(new FileInputStream(input));
                       OutputStream out = new BufferedOutputStream(new GZIPOutputStream(
                               new FileOutputStream(output)
                       ));
                        ) {
                    int b;
                    while ((b = in.read()) != -1) {
                        out.write(b);
                    }
                    out.flush();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
