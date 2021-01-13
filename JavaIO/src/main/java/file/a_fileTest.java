package file;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/12/30
 **/

public class a_fileTest {

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
         * 构造File对象时，既可以传入绝对路径，也可以传入相对路径。
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

        traverseFiles();  //遍历文件和目录

        /**
         * Java标准库还提供了一个Path对象，它位于java.nio.file包
         * Path对象和File对象类似
         */
        System.out.println("====pathTest();====");
        pathTest();
    }

    /**
     * 遍历文件和目录
     */
    private static void traverseFiles() {
        File f = new File("C:\\windows");
        File[] fs1 = f.listFiles();  //列出文件和子目录
        printFiles(fs1);
        File[] fs2 = f.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".exe");
            }
        });
        printFiles(fs2);
    }

    private static void printFiles(File[] files) {
        System.out.println("================");
        if (files != null)
            for (File file:files)
                System.out.println(file);
        System.out.println("================");
    }

    private static void pathTest() {
        Path p1 = Paths.get(".", "project", "study"); // 构造一个Path对象
        System.out.println(p1);
        Path p2 = p1.toAbsolutePath(); // 转换为绝对路径
        System.out.println(p2);
        Path p3 = p2.normalize(); // 转换为规范路径
        System.out.println(p3);
        File f = p3.toFile(); // 转换为File对象
        System.out.println(f);
        for (Path p : Paths.get("..").toAbsolutePath()) { // 可以直接遍历Path
            System.out.println("  " + p);
        }
    }
}



