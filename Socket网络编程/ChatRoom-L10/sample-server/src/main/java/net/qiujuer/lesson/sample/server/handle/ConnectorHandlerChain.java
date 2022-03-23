package net.qiujuer.lesson.sample.server.handle;

import net.qiujuer.library.clink.core.Connector;

/**
 * 责任链默认结构封装
 * @param <Model>
 */
public abstract class ConnectorHandlerChain<Model> {
    // 当前节点所持有的小份节点
    private volatile ConnectorHandlerChain<Model> next;

    /**
     * 添加一个新的节点到当前的链式结构的末尾
     * @param newChain
     * @return
     */
    public ConnectorHandlerChain<Model> appendLast(ConnectorHandlerChain<Model> newChain) {
        // 添加前，优先判断是否是当前实例
        // 同时在一个链式结构中只能存在某一个节点的一个实例，使用Class区分
        if (newChain == this || this.getClass().equals(newChain.getClass())) {
            return this;
        }
        synchronized (this) {
            // 下一个节点是null，则追加
            if (next == null) {
                next = newChain;
                return newChain;
            }
            // 否则让下一个节点进行新节点的添加，将责任后推
            return next.appendLast(newChain);
        }
    }

    /**
     * PS1：移除节点中的某一个节点及其后续节点
     * PS2：移除某节点时，如果其具有后续节点，则将后续节点接到当前节点上；实现移除中间某节点的操作
     * @param clx
     * @return
     */
    public boolean remove(Class<? extends ConnectorHandlerChain<Model>> clx) {
        // 自己不能移除自己，因为自己未持有上一个链接的引用
        if (this.getClass().equals(clx)) {
            return false;
        }
        synchronized (this) {
            if (next == null) {
                // 当前无下一个节点存在，无法判断
                return false;
            } else if (next.getClass().equals(clx)) {
                // 移除next节点
                next = next.next;
                return true;
            } else {
                // 交给next进行移除操作
                return next.remove(clx);
            }
        }
    }

    /**
     * 优先自己消费，如果自己未消费，则交给next消费
     * 若：next=null 或 next未消费，则回调 consumeAgain
     * @param handler
     * @param model
     * @return
     */
    synchronized public boolean handle(ClientHandler handler, Model model) {
        ConnectorHandlerChain<Model> next = this.next;
        // 自己能消费就自己消费
        if (consume(handler, model)) {
            return true;
        }
        boolean consumed = next != null && next.handle(handler, model);
        if (consumed) {
            return true;
        }
        return consumeAgain(handler, model);
    }

    protected abstract boolean consume(ClientHandler handler, Model model);

    protected boolean consumeAgain(ClientHandler handler, Model model) {
        return false;
    }
}
