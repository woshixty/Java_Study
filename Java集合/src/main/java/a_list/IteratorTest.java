package a_list;

import java.util.HashSet;
import java.util.Iterator;

public class IteratorTest {
    public static void main(String[] args) {
        //创建一个集合
        HashSet<Object> books = new HashSet<>();
        books.add("轻量级Java EE企业级应用实战");
        books.add("疯狂Java讲义");
        books.add("疯狂Android讲义");
        //获取books对象的迭代器
        Iterator<Object> it = books.iterator();
        while(it.hasNext()) {
            //强制转换
            String book = (String) it.next();
            System.out.println(book);
            if (book.equals("疯狂Java讲义")) {
                //从集合中删除上一次next()方法返回的元素
                it.remove();
            }
            //为测试变量赋值，不会改变几个元素本身
            book = "测试字符串";
        }
        System.out.println(books);
    }
}
