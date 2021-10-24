package net.qiujuer.library.clink.box;

import net.qiujuer.library.clink.core.ReceivePacket;
import sun.management.counter.perf.PerfInstrumentation;

/**
 * @author qyyzxty@icloud.com
 * @data 2021/10/24
 **/
public class StringReceivePacket extends ReceivePacket {
    private byte[] buffer;
    private int position;

    public StringReceivePacket(int len) {
        this.buffer = new byte[len];
        length = len;
    }

    @Override
    public void save(byte[] bytes, int count) {
        System.arraycopy(bytes, 0, buffer, position, count);
        position += count;
    }

    public String string() {
        return new String(buffer);
    }

    public void setBuffer(int len) {
        this.buffer = new byte[len];
    }
}
