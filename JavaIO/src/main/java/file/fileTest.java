package file;

import java.io.File;
import java.io.IOException;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/12/30
 **/

public class fileTest {
    public static void createFile(String path) throws IOException {
    File file = new File(path);
    file.createNewFile();
}

    public static void deleteFile(String path) {
        File file = new File(path);
        file.delete();
    }

    public static void main(String[] args) throws IOException {
        /**
         * File对象既可以表示文件，也可以表示目录。
         * 特别要注意的是，构造一个File对象，即使传入的文件或目录不存在，代码也不会出错，因为构造一个File对象，并不会导致任何磁盘操作。
         * 只有当我们调用File对象的某些方法的时候，才真正进行磁盘操作。
         */
        File file = new File("src\\main\\resources\\test.txt");
        System.out.println(file.isFile());
        System.out.println(file.isDirectory());
        /**
         * 用File对象获取到一个文件时，还可以进一步判断文件的权限和大小：
         * boolean canRead()：是否可读；
         * boolean canWrite()：是否可写；
         * boolean canExecute()：是否可执行；
         * long length()：文件字节大小；
         */
        System.out.println("绝对路径"+file.getAbsolutePath()+"    相对路径"+file.getPath()+"    规范路径"+file.getCanonicalPath());

        createFile("src\\main\\resources\\create.txt");
        deleteFile("src\\main\\resources\\create.txt");
    }
}



