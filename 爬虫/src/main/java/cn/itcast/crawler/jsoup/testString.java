package cn.itcast.crawler.jsoup;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/12/27
 **/
public class testString {
    public static void main(String[] args) throws IOException {
        //使用工具类读取文件，获取字符串
        String content = FileUtils.readFileToString(new File("C:\\Users\\qyyzx\\Desktop\\index.html"), "utf8");

        //解析字符串
        Document document = Jsoup.parse(content);
        String title = document.getElementsByTag("title").first().text();

        System.out.println(title);
    }
}
