package cn.itcast.netty.c1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class TestFileChannelTransferTo {
    public static void main(String[] args) {
        try(
                FileChannel from = new FileInputStream("data.txt").getChannel();
                final FileChannel to = new FileOutputStream("to.txt").getChannel()
        ) {
            //效率高，底层会利用操作系统的零拷贝进行优化，一次最多传2G
            from.transferTo(0, from.size(), to);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
