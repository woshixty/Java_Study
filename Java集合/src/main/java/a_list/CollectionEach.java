package a_list;

import java.util.HashSet;

public class CollectionEach {
    public static void main(String[] args) {
        //创建一个集合
        HashSet<Object> books = new HashSet<>();
        books.add("轻量级Java EE企业级应用实战");
        books.add("疯狂Java讲义");
        books.add("疯狂Android讲义");
        //遍历forEach()方法遍历集合
        books.forEach(obj -> {
            System.out.println("迭代集合元素：" + obj);
        });
    }
}
