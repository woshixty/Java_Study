import java.lang.annotation.*;

/**
 * @author qyyzxty@icloud.com
 * @data 2021/1/26
 **/

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface inheritedTest {
    String name() default "WUST";
}

