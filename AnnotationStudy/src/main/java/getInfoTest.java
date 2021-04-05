import javax.swing.text.html.HTML;
import java.lang.annotation.Annotation;

/**
 * @author qyyzxty@icloud.com
 * @data 2021/1/26
 **/
public class getInfoTest {

    @inheritedTest  // 该注解具有继承性
    class father {
        public void info() {
            System.out.println("Hello");
        }
    }

    // 继承father类
    class son extends father {
        @Override
        public void info() {
            System.out.println("Java");
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
        // 获取son类上面的所有注解
        Annotation[] aArray = Class.forName("son").getAnnotations();
        // 遍历所有注解
        for (Annotation a:aArray) {
            System.out.println(a);
            // 如果注解是@inheritedTest类型
            if (a instanceof inheritedTest)
                // 强转为@inheritedTest类型并取出@inheritedTest中的name值
                System.out.println(((inheritedTest) a).name());
        }
    }
}
