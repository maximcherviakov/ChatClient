package org.example;

public class Main {
    public static void main(String[] args) {
        ConnectInputMessage connectWithServer = new ConnectInputMessage();
        Thread connectInputMessageThread = new Thread(connectWithServer);
        connectInputMessageThread.start();

        ReceiveMessageFromServer receiveMessage = new ReceiveMessageFromServer(connectWithServer.getInputStreamServer());
        Thread receiveMessageThread = new Thread(receiveMessage);
        receiveMessageThread.start();
    }
}