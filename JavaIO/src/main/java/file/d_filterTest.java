package file;

/**
 * @author qyyzxty@icloud.com
 * @data 2021/1/13
 *
 * Java的IO标准库提供的InputStream根据来源可以包括：
 *
 * FileInputStream：从文件读取数据，是最终数据源；
 * ServletInputStream：从HTTP请求读取数据，是最终数据源；
 * Socket.getInputStream()：从TCP连接读取数据，是最终数据源；
 * ...
 * 如果我们要给FileInputStream添加缓冲功能，则可以从FileInputStream派生一个类：
 * BufferedFileInputStream extends FileInputStream
 **/

public class d_filterTest {
}
