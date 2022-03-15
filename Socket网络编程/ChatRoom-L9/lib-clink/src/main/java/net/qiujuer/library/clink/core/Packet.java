package net.qiujuer.library.clink.core;

import java.io.Closeable;
import java.io.IOException;
import java.util.stream.Stream;

/**
 * 公共数据的封装
 * 提供了类型以及基本的长度的定义
 */
public abstract class Packet<Stream extends Closeable> implements Closeable {
    protected byte type;
    protected long length;
    private Stream stream;

    public byte type() {
        return type;
    }

    public long length() {
        return length;
    }

    /**
     * 打开一个流
     * @return
     */
    public final Stream open() {
        if (stream == null) {
            stream = createStream();
        }
        return stream;
    }

    /**
     * 关闭操作
     * @throws IOException
     */
    @Override
    public final void close() throws IOException {
        if (stream != null) {
            closeStream(stream);
            stream = null;
        }
    }

    /**
     * 抽象方法：创建流
     * @return
     */
    protected abstract Stream createStream();

    /**
     * 关闭流
     * @return
     */
    protected void closeStream(Stream stream) throws IOException {
        stream.close();
    }
}
