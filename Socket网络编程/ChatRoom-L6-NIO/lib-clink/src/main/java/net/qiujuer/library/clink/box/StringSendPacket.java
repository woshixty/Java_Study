package net.qiujuer.library.clink.box;

import net.qiujuer.library.clink.core.SendPacket;
import sun.security.util.Length;

import java.io.IOException;

/**
 * @author qyyzxty@icloud.com
 * @data 2021/10/24
 **/
public class StringSendPacket extends SendPacket {
    private final byte[] bytes;

    public StringSendPacket(String msg) {
        this.bytes = msg.getBytes();
        this.length = bytes.length;
    }

    @Override
    public byte[] bytes() {
        return new byte[0];
    }

    public byte[] getBytes() {
        return bytes;
    }

    @Override
    public void close() throws IOException {

    }
}
