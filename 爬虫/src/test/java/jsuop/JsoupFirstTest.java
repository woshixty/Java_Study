package jsuop;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.net.URL;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/12/21
 **/
public class JsoupFirstTest {

    @Test
    public void testUrl() throws Exception {
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

    @Test
    public void testString() throws Exception{
        //使用工具类读取文件，获取字符串
        String content = FileUtils.readFileToString(new File("C:\\Users\\qyyzx\\Desktop\\index.html"), "utf8");

        //解析字符串
        Document document = Jsoup.parse(content);
        String title = document.getElementsByTag("title").first().text();

        System.out.println(title);
    }

    @Test
    public void testFile() throws Exception {
        //解析文件
        Document document = Jsoup.parse(new File("C:\\Users\\qyyzx\\Desktop\\index.html"), "utf8");

        //解析
        String title = document.getElementsByTag("title").first().text();

        System.out.println(title);
    }

    @Test
    public void testDom() throws Exception {
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

    @Test
    public void testData() throws Exception {
        //获取元素之后，得从元素中获取数据

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

        //-------------------------------------------------------------
        //-------------------------------------------------------------
        System.out.println("--------从元素中获取数据--------");

        //从元素中获取数据
        //1.从元素中获取id
        System.out.println(element.id());

        //2.获取className
        System.out.println(element.className());

        //3.获取属性的值attr
        System.out.println(element.attr("id"));

        //4.获取所有属性
        System.out.println(element.attributes());

        //5.获取文本内容
        System.out.println(element.text());
    }

    @Test
    public void testSelect() {
    }
}
