package a_list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author qyyzxty@icloud.com
 * @data 2021/1/13
 * 使用List
 * 在集合类中，List是最基础的一种集合：它是一种有序列表
 **/
public class listTest {
    /**
     * List的行为和数组几乎完全相同：
     * List内部按照放入元素的先后顺序存放，每个元素都可以通过索引确定自己的位置，List的索引和数组一样，从0开始
     * 数组和List类似，也是有序结构
     * 如果我们使用数组，在添加和删除元素的时候，会非常不方便。
     * 例如，从一个已有的数组{'A', 'B', 'C', 'D', 'E'}中删除索引为2的元素
     * 这个“删除”操作实际上是把'C'后面的元素依次往前挪一个位置，
     * 而“添加”操作实际上是把指定位置以后的元素都依次向后挪一个位置，腾出来的位置给新加的元素
     * 这两种操作，用数组实现非常麻烦
     */

    /**
     * 这两种操作，用数组实现非常麻烦
     * 实际上，ArrayList在内部使用了数组来存储所有元素
     * ArrayList把添加和删除的操作封装起来，让我们操作List类似于操作数组，却不用关心内部元素如何移动
     *
     * 我们考察List<E>接口，可以看到几个主要的接口方法：
     *
     * 在末尾添加一个元素：boolean add(E e)
     * 在指定索引添加一个元素：boolean add(int index, E e)
     * 删除指定索引的元素：int remove(int index)
     * 删除某个元素：int remove(Object e)
     * 获取指定索引的元素：E get(int index)
     * 获取链表大小（包含元素的个数）：int size()
     */

    /**
     * 但是，实现List接口并非只能通过数组（即ArrayList的实现方式）来实现，
     * 另一种LinkedList通过“链表”也实现了List接口
     */

    /**
     * list的特点：
     * 1.允许添加重复元素
     * 2.允许添加null
     */
    public static void Test1() {
        List<String> list = new ArrayList<>();
        list.add("apple");
        list.add("pear");
        list.add("apple");
        list.add(1,"xty");
        list.add(null);
        list.forEach(System.out::println);
        System.out.println(list.size());
    }

    /**
     * 创建list
     * 除了使用ArrayList和LinkedList，我们还可以通过List接口提供的of()方法，根据给定元素快速创建List
     */
    public static void Test2() {
        // List<Integer> list = List.of();  //JDK太老了，起码得15以上
        // 此方法不接受NULL
    }

    /**
     * 遍历List
     * 和数组类型，我们要遍历一个List，完全可以用for循环根据索引配合get(int)方法遍历
     */
    public static void Test3() {
        // List<String> list = List.of("apple", "pear", "banana");
        List<String> list = new ArrayList<>();
        list.add("apple");
        list.add("pear");
        list.add("bear");
        for (int i=0; i<list.size(); i++) {
            String s = list.get(i);
            System.out.println(s);
        }
        // 这种方法不推荐
        /**
         * 使用迭代器
         * Iterator本身也是一个对象，但它是由List的实例调用iterator()方法的时候创建的
         * Iterator对象知道如何遍历一个List，并且不同的List类型，返回的Iterator对象实现也是不同的，但总是具有最高的访问效率
         * Iterator对象有两个方法：boolean hasNext()判断是否有下一个元素，E next()返回下一个元素
         * 使用Iterator遍历List代码如下
         */
        for (Iterator<String> iterator = list.iterator(); iterator.hasNext(); ) {
            String s = iterator.next();
            System.out.println(s);
        }

        /**
         * 有童鞋可能觉得使用Iterator访问List的代码比使用索引更复杂
         * 但是，要记住，通过Iterator遍历List永远是最高效的方式
         * 并且，由于Iterator遍历是如此常用
         * 所以，Java的for each循环本身就可以帮我们使用Iterator遍历
         *
         * 实际上，只要实现了Iterable接口的集合类都可以直接用for each循环来遍历
         * Java编译器本身并不知道如何遍历集合对象，但它会自动把for each循环变成Iterator的调用
         * 原因就在于Iterable接口定义了一个Iterator<E> iterator()方法
         * 强迫集合类必须返回一个Iterator实例。
         */
    }

    /**
     * List和Array转换
     *
     */
    public static void Test4() {
        
    }

    public static void main(String[] args) {
        Test1();

        Test3();
    }
}
