package net.qiujuer.lesson.sample.server;

import net.qiujuer.lesson.sample.foo.handle.ConnectorHandler;
import net.qiujuer.lesson.sample.foo.handle.ConnectorStringPacketChain;
import net.qiujuer.library.clink.box.StringReceivePacket;

import java.util.ArrayList;
import java.util.List;

public class Group {
    // 群名
    private final String name;
    private final GroupMessageAdapter adapter;
    // 群成员
    private final List<ConnectorHandler> members = new ArrayList<>();

    public Group(String name, GroupMessageAdapter adapter) {
        this.name = name;
        this.adapter = adapter;
    }

    String getName() {
        return name;
    }

    boolean addMember(ConnectorHandler handler) {
        synchronized (members) {
            if (!members.contains(handler)) {
                members.add(handler);
                // 添加一个链式结构
                handler.getStringPacketChain().appendLast(new ForwardConnectorStringPacketChain());
                System.out.println("Group[" + name + "] add new member:" + handler.getClientInfo());
                return true;
            }
        }
        return false;
    }

    boolean removeMember(ConnectorHandler handler) {
        synchronized (members) {
            if (members.remove(handler)) {
                handler.getStringPacketChain().remove(ForwardConnectorStringPacketChain.class);
                System.out.println("Group[" + name + "] leave member:" + handler.getClientInfo());
                return true;
            }
        }
        return false;
    }

    private class ForwardConnectorStringPacketChain extends ConnectorStringPacketChain {
        @Override
        protected boolean consume(ConnectorHandler handler, StringReceivePacket stringReceivePacket) {
            synchronized (members) {
                for (ConnectorHandler member : members) {
                    if (member == handler)
                        continue;
                    adapter.senMessageToClient(member, stringReceivePacket.entity());
                }
                return true;
            }
        }
    }

    interface GroupMessageAdapter {
        void senMessageToClient(ConnectorHandler handler, String msg);
    }
}
