package cn.itcast.crawler.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.net.URL;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/12/27
 **/
public class testUrl {
    public static void main(String[] args) throws IOException {
        /**
         * 解析URL地址
         * @URL 访问的url
         * @TimeoutMillis 超时时间
         * 获得一个 Document 对象
         */
        Document document = Jsoup.parse(new URL("http://www.itcast.cn"), 1000);

        /**
         * 使用标签选择器 获取标签中的内容
         */
        String title = document.getElementsByTag("title").first().text();
        System.out.println(title);
    }
}
