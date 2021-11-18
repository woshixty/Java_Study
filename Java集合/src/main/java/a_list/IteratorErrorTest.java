package a_list;

import java.util.HashSet;
import java.util.Iterator;

public class IteratorErrorTest {
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
            books.remove(book);
        }
        System.out.println(books);
    }
}
