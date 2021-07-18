package cn.itcast.crawler.file;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

/**
 * @author qyyzxty@icloud.com
 * @data 2021/2/28
 **/
public class SaveTheHtmlInFile {
    public static void main(String[] args) {
        try {
            //获取响应
            Connection.Response response = Jsoup.connect("https://www.cnblogs.com/vitoboy/p/12657571.html")
                    .method(Connection.Method.GET).execute();
            //查看url等信息
            URL url = response.url();
            int statusCode = response.statusCode();
            String contentType = response.contentType();
            String statusMessage = response.statusMessage();

            //输出信息
            System.out.println(
                    "url:"+url+'\n'+
                    "statusCode:"+statusCode+'\n'+
                    "contentType:"+contentType+'\n'+
                    "statusMessage:"+statusMessage
            );

            //200
            if ( statusCode == 200 ) {
                //获得响应的html文件
                String html = new String( response.bodyAsBytes() );
                //获取html文件，对应的是Document类型的
                Document document = response.parse();
                /**
                 * html和document数据一样但是，Document经过了格式化处理
                 */
                System.out.println( html );
                System.out.println( document );

                OutputStream stringOut = new FileOutputStream("src\\main\\resources\\string.html", true);
                stringOut.write( html.getBytes("UTF-8") );
                stringOut.close();

                OutputStream documentOut = new FileOutputStream("src\\main\\resources\\document.html");
                documentOut.write( document.html().getBytes() );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
