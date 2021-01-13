package file;

import java.io.*;
import java.util.Arrays;

/**
 * @author qyyzxty@icloud.com
 * @data 2021/1/13
 *
 * 序列化
 * 序列化是指把一个Java对象变成二进制内容，本质上就是一个byte[]数组
 * 序列化后可以把byte[]保存到文件中，或者把byte[]通过网络传输到远程
 * 这样，就相当于把Java对象存储到文件或者通过网络传输出去了
 *
 * 反序列化，即把一个二进制内容（也就是byte[]数组）变回Java对象
 * 有了反序列化，保存到文件中的byte[]数组又可以“变回”Java对象，或者从网络上读取byte[]并把它“变回”Java对象
 **/

public class f_serializeTest {
    public static void main(String[] args) {

        /**
         * 我们来看看如何把一个Java对象序列化
         * 一个Java对象要能序列化，必须实现一个特殊的java.io.Serializable接口
         * Serializable接口没有定义任何方法，它是一个空接口
         * 我们把这样的空接口称为“标记接口”（Marker Interface）
         * 实现了标记接口的类仅仅是给自身贴了个“标记”，并没有增加任何方法
         */
        // 把一个Java对象变为byte[]数组，需要使用ObjectOutputStream
        // 它负责把一个Java对象写入一个字节流
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try(ObjectOutputStream output = new ObjectOutputStream(buffer)) {
            // 写入int
            output.writeInt(12345);
            // 写入string
            output.writeUTF("hello java");
            // 写入object
            output.writeObject(Double.valueOf(123.123));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(Arrays.toString(buffer.toByteArray()));
        /**
         * ObjectOutputStream既可以写入基本类型，如int，boolean，也可以写入String（以UTF-8编码），
         * 还可以写入实现了Serializable接口的Object
         * 因为写入Object时需要大量的类型信息，所以写入的内容很大
         */

        /**
         * 反序列化
         * 和ObjectOutputStream相反，ObjectInputStream负责从一个字节流读取Java对象
         */
//        try(ObjectInputStream inputStream = new ObjectInputStream()) {
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        /**
         * 可序列化的Java对象必须实现java.io.Serializable接口，类似Serializable这样的空接口被称为“标记接口”（Marker Interface）；
         * 反序列化时不调用构造方法，可设置serialVersionUID作为版本号（非必需）；
         * Java的序列化机制仅适用于Java，如果需要与其它语言交换数据，必须使用通用的序列化方法，例如JSON。
         */
    }
}
