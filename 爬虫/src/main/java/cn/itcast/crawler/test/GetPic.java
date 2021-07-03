package cn.itcast.crawler.test;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import java.io.*;

/**
 * @author qyyzxty@icloud.com
 * @data 2021/4/6
 **/
public class GetPic {

    public static void main(String[] args) throws IOException {
        String url = "https://www.qyyzxty.xyz/VolunteerFiles/4.jpg";
        Connection.Response response = Jsoup.connect(url).timeout(10*60*1000)
                .maxBodySize(Integer.MAX_VALUE)
                .method(Connection.Method.GET).ignoreContentType(true).execute();
        if ( response.statusCode() == 200 ) {
            //响应转化成输出流
            BufferedInputStream inputStream = response.bodyStream();
            //保存图片
            //一次最多读取1kb
            byte[] buffer = new byte[1024];
            int len = 0;
            //创建缓冲流
            FileOutputStream fileOutputStream = new FileOutputStream(new File("C:\\Users\\qyyzx\\Desktop\\hello1.jpg"));
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            //文件写入
            while ((len = inputStream.read(buffer, 0, 1024)) != -1) {
                bufferedOutputStream.write(buffer, 0, len);
            }
            //缓冲流释放与关闭
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        }
    }

}
