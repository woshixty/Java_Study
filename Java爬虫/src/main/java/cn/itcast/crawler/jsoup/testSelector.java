package cn.itcast.crawler.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.IOException;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/12/27
 **/

public class testSelector  {
    // selector 选择器
    public static void main(String[] args) throws IOException {
        // 解析 html 页面，获取 document 对象

        //绝对路径
        Document document = Jsoup.parse(new File("C:\\Users\\qyyzx\\Desktop\\index.html"), "utf8");

        //相对路径
        document = Jsoup.parse(new File("src\\main\\resources\\index.html"), "utf8");

        //tagName 通过标签查找元素，比如：span
        Elements elements = document.select("h4");
        System.out.println(elements.text());
        elements.forEach(System.out::println);

        //#id 通过id查找元素 比如 #hhhh
        System.out.println(document.getElementById("hhhh"));

        System.out.println("------------ calss ------------");

        //.class 痛过 class 名称来查
        System.out.println(document.select(".list_word"));

        //[attribute]: 利用属性查找元素
        System.out.println(document.select("[src]"));

        System.out.println("------------ attr=value ------------");

        //[attr=value]: 利用属性值来查找元素
        System.out.println(document.select("[class=box_in_word2]#xty"));

        /**
         * selector
         * 组合使用
         */
        System.out.println('\n'+'\n');
        System.out.println("------------ 以下为selector的组合使用 ------------");

        //el#id: 元素+ID，比如：h3#city_bj
        System.out.println(document.select("div#left_box"));

        System.out.println("-------- class --------");

        //el.class: 元素+class，例如: li.class
        System.out.println(document.select("p.list_word"));

        System.out.println("-------- 任意组合 --------");

        //任意组合: 比如span[abc].s_name
        System.out.println(document.select("a#xty[href=https://www.yinxiang.com/product/main/]"));

        System.out.println("------------------------");

        // ancestor child: 查找某个元素下的子元素 例如：.city_con li 查找city_con下的所有li
        System.out.println(document.select("div#left_box"));

        System.out.println("------------------------");

        // parent > child: 查找父元素下的！！！直接子元素！！！，比如：.city_con > ul > li 查找city_con的第一级别，再找ul的第一级li
        System.out.println(document.select("div#left_box > div"));

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++");

        // parent > *: 查找某个父元素下的所有直接子元素，注意！！！直接子元素！！！
        System.out.println(document.select("div#left_box > *"));
    }
}
