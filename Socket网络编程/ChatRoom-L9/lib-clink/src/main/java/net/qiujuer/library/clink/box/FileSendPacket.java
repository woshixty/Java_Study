package net.qiujuer.library.clink.box;

import net.qiujuer.library.clink.core.SendPacket;

import java.io.*;

/**
 * 文件发送实现类
 */
public class FileSendPacket extends SendPacket<FileInputStream> {
    public FileSendPacket(File file) {
        this.length = file.length();
    }

    @Override
    protected Closeable createStream() {
        return null;
    }
}
