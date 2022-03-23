package net.qiujuer.lesson.sample.server.handle;

/**
 * 关闭链式结构
 */
public class DefaultPrintConnectorCloseChain extends ConnectorHandlerChain {

    @Override
    protected boolean consume(ClientHandler handler, Object o) {
        System.out.println(handler.getClientInfo() + "Exit!!, Key:" + handler.getKey());
        return false;
    }
}
