package fengkuang;

import java.io.File;
import java.io.IOException;

/**
 * @author qyyzxty@icloud.com
 * 2021/5/12
 **/
public class JY_File {
    public static void main(String[] args) throws IOException {
        File file = new File("/Users/mr.stark/Desktop/Hello.txt");
        //返回文件名
        System.out.println(file.getName());
        //File对象对应的路径名
        System.out.println(file.getPath());
        //File对象的绝对路径
        System.out.println(file.getAbsolutePath());
        //File对象的父目录
        System.out.println(file.getParent());
        //重命名文件对象（目录或者文件）
//        file.renameTo(new File("/Users/mr.stark/Desktop/Hello.txt"));

        //文件是否存在
        file.exists();
        //文件是否可写
        file.canWrite();
        //判断是否是一个文件（非目录）
        file.isFile();
        //判断是否是一个目录（非文件）
        file.isDirectory();
        //判断该对象对应的文件或目录是否是绝对路径
        file.isAbsolute();

        //文件最后修改时间
        System.out.println(file.lastModified());
        //文件内容长度
        System.out.println(file.length());

        //创建
        file.createNewFile();
        //删除
        file.delete();
        //创建临时文件
        File.createTempFile("temp-test", "txt");
        //注册一个删除钩子，Java虚拟机退出时删除File文件对应的文件或目录
        file.deleteOnExit();

        file = new File("/Users/mr.stark/Desktop");
        //创建FIle对象对应的目录
        file.mkdir();
        //列出File对象所有的子文件和路径
        String[] strings = file.list();
        for (String string : strings) {
            System.out.println(string);
        }
        System.out.println("++++++++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++++++++");
        //列出File对象所有的子文件和路
        File[] files = file.listFiles();
        for (File file1 : files) {
            System.out.println(file1);
        }
        File f = new File(".");
        //使用lambda表达式实现文件过滤
        String[] nameList = f.list(
                //如果是java文件或者是一个目录就通过
                (dir, name) -> name.endsWith(".java") || new File(name).isDirectory()
        );
        for (String s : nameList) {
            System.out.println(s);
        }
    }
}
