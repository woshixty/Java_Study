import java.util.ArrayList;
import java.util.List;

/**
 * @author qyyzxty@icloud.com
 * @data 2021/1/26
 **/

@inheritedTest
class father {
    public void info() {
        System.out.println("Hello");
    }
}

class son extends father {
   @Override
   public void info() {
       System.out.println("Java");
   }
}

@SuppressWarnings( value = "unchecked")
class Test {
    public void test() {
        List<String> myList = new ArrayList();
    }
}

@FunctionalInterface
interface func {
    static void a() {
        System.out.println("a");
    }
    default void b() {
        System.out.println("b");
    }
    //唯一的抽象方法
    void c();
}