package study_one;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Study_5 {

    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Date d = new Date();
        SimpleDateFormat f = new SimpleDateFormat("今天是yyyy年MM月dd日 E kk点mm分ss秒");
        System.out.println(d.toString());
        System.out.println(d.toLocaleString());
        System.out.println(d.toGMTString());
        System.out.println(f.format(d));
    }

}
