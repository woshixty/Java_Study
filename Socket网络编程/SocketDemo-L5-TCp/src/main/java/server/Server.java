package server;

import constants.TCPConstants;
import constants.UDPConstants;

import java.io.IOException;

public class Server {
    public static void main(String[] args) {
        TCPServer tcpServer = new TCPServer(TCPConstants.PORT_SERVER);
        boolean isSucceed = tcpServer.start();
        if (!isSucceed) {
            System.out.println("Start TCP server failed!");
            return;
        }
        UDPProvider.start(UDPConstants.PORT_SERVER);
        try {
            //noinspection ResultOfMethodCallIgnored
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        UDPProvider.stop();
        tcpServer.stop();
    }
}
