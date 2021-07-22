package UDPEXPER;

/**
 * @author qyyzxty@icloud.com
 * 2021/7/20
 **/
public class MessageCreator {
    private static final String SN_HEADER = "收到暗号，我是（SN）：";
    private static final String PORT_HEADER = "这是暗号，请回电端口（Port）：";

    /**
     * 基于端口号的创建
     * @param port
     * @return
     */
    public static String buildWithPort(int port) {
        return PORT_HEADER + port;
    }

    /**
     * 解析端口
     * @param data
     * @return
     */
    public static int parsePort(String data) {
        if (data.startsWith(PORT_HEADER))
            return Integer.parseInt(data.substring(PORT_HEADER.length()));
        return -1;
    }

    /**
     * 创建信息
     * @param sn
     * @return
     */
    public static String buildWithSn(String sn) {
        return SN_HEADER + sn;
    }

    /**
     * 解析信息
     * @param data
     * @return
     */
    public static String parseSn(String data) {
        if (data.startsWith(SN_HEADER)) {
            return data.substring(SN_HEADER.length());
        }
        return null;
    }
}
