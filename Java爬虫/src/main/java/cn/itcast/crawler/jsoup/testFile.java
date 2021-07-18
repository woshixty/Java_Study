package cn.itcast.crawler.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/12/27
 **/
public class testFile {
    public static void main(String[] args) throws IOException {
        //解析文件
        Document document = Jsoup.parse(new File("C:\\Users\\qyyzx\\Desktop\\index.html"), "utf8");
        System.out.println(document);

        //解析
        String title = document.getElementsByTag("title").first().text();
        System.out.println(title);
    }
}
