package fengkuang;

import java.io.*;

/**
 * @author qyyzxty@icloud.com
 * 2021/5/20
 * Java9增加的过滤功能
 **/
public class FilterTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //创建一个ObjectInputStream输入流
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("object.txt"));
        ois.setObjectInputFilter( (info) -> {
            System.out.println("===执行数据过滤===");
            ObjectInputFilter serialFilter = ObjectInputFilter.Config.getSerialFilter();
            if (serialFilter != null) {
                //首先使用ObjectInputFilter执行默认的检查
                ObjectInputFilter.Status status = serialFilter.checkInput(info);
                //如果默认检查的结果不是UNDECIDED
                if (status != ObjectInputFilter.Status.UNDECIDED) {
                    //返回检查的结果
                    return status;
                }
            }
            //要恢复的对象不是1个
            if (info.references() != 1) {
                //不允许恢复对象
                return ObjectInputFilter.Status.REJECTED;
            }
            if (info.serialClass() != null && info.serialClass() != Person.class) {
                //如果恢复的对象不是Person类则拒绝恢复
                return ObjectInputFilter.Status.REJECTED;
            }
            return ObjectInputFilter.Status.UNDECIDED;
        } );
        //从输入流中读取一个Java对象，并将其强制转换为Person
        Person p = (Person) ois.readObject();
        System.out.println(p.getName()+ " " +p.getAge());
    }
}
