package net.qiujuer.lesson.sample.foo.handle;

import net.qiujuer.library.clink.core.Connector;

/**
 * 关闭链式结构
 */
public class DefaultPrintConnectorCloseChain extends ConnectorCloseChain {

    @Override
    protected boolean consume(ConnectorHandler handler, Connector connector) {
        System.out.println(handler.getClientInfo() + "Exit!!, Key:" + handler.getKey());
        return false;
    }
}
