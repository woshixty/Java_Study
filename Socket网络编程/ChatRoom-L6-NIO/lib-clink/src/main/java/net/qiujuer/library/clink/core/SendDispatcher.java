package net.qiujuer.library.clink.core;

/**
 * @author qyyzxty@icloud.com
 * @data 2021/10/24
 * 发送数据的调度者
 * 缓存所有需要发送的数据，通过队列进行发送
 * 并且在发送数据时，实现是对手数据的基本封装
 **/
public interface SendDispatcher {
    /**
     * 发送一份数据
     * @param packet
     */
    void send(SendPacket packet);

    /**
     * 取消发送数据
     * @param packet
     */
    void cancel(SendPacket packet);
}
