package cn.itcast.crawler.file;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import java.io.*;

/**
 * @author qyyzxty@icloud.com
 * @data 2021/2/28
 **/
public class GetPics {
    public static void saveImage(BufferedInputStream in, String savePath) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        //创建缓冲流
        FileOutputStream fileOutputStream = new FileOutputStream( new File( savePath ));
        BufferedOutputStream bufferedOut = new BufferedOutputStream( fileOutputStream );
        //图片写入
        while ( ( len = in.read( buffer, 0, 1024 ) ) != -1 ) {
            bufferedOut.write( buffer, 0, len );
        }
        //缓冲流释放与关闭
        bufferedOut.flush();
        bufferedOut.close();
    }

    public static void main(String[] args) throws IOException {
        String imageUrl = "https://gimg2.baidu.com/image_search/" +
                "src=http%3A%2F%2Fa0.att.hudong.com%2F30%2F29%2F01300000201438121627296084016.jpg" +
                "&refer=http%3A%2F%2Fa0.att.hudong.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg" +
                "?sec=1617086482&t=a63b9eb76153eb8281f4078b682401a9";
        Connection connection = Jsoup.connect(imageUrl);
        Connection.Response response = connection.method( Connection.Method.GET ).ignoreContentType(true).execute();
        System.out.println("文件类型："+response.contentType());
        String ss = response.contentType();
        System.out.println( ss );
        String s = ss.substring( ss.indexOf('/')+1 );
        System.out.println( s );
        BufferedInputStream bufferedInputStream = response.bodyStream();
        //保存图片
        String path = "C:\\Users\\qyyzx\\Desktop\\12.jepg";
        saveImage( bufferedInputStream,  path);
    }
}
