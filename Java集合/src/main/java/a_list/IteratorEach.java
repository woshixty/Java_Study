package a_list;

import java.util.HashSet;
import java.util.Iterator;

public class IteratorEach {
    public static void main(String[] args) {
        //创建一个集合
        HashSet<Object> books = new HashSet<>();
        books.add("轻量级Java EE企业级应用实战");
        books.add("疯狂Java讲义");
        books.add("疯狂Android讲义");
        //获取books对应的迭代器
        Iterator<Object> iterator = books.iterator();
        //使用Lambda表达式来遍历集合元素
        iterator.forEachRemaining(obj -> {
            System.out.println("迭代元素集合：" + obj);
        });
    }
}
