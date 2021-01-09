package cn.itcast.crawler.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/12/27
 **/
public class testDom {
    public static void main(String[] args) throws IOException {
        //解析文件
        Document document = Jsoup.parse(new File("C:\\Users\\qyyzx\\Desktop\\index.html"), "utf8");

        //获取元素
        //1.跟据id查询
        Element element = document.getElementById("hhhh");

        //2.跟据标签来获取元素
        Elements elements = document.getElementsByTag("a");

        //3.跟据class获取元素
        Elements box_in_word3 = document.getElementsByClass("box_in_word3");

        //4.跟据属性获取元素
        Elements attribute = document.getElementsByAttribute("width");
        //属性名与属性值
        Elements style = document.getElementsByAttributeValue("style", "font-size: 30px;");

        System.out.println(element.text());
        System.out.println(elements.text());
        System.out.println(box_in_word3.text());
        System.out.println(attribute);
        System.out.println(style);
    }
}
