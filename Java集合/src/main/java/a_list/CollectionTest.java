package a_list;

import java.util.Arrays;
import java.util.List;

public class CollectionTest {
    public static void main(String[] args) {
        var strColl = List.of("Java", "Kotlin", "Swift", "Python");
        String[] sa = strColl.toArray(String[]::new);
        System.out.println(Arrays.toString(sa));
    }
}
