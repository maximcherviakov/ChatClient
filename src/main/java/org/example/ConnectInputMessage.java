package org.example;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectInputMessage implements Runnable {
    private static final String HOST = "localhost";
    private static final int PORT = 8893;

    private Socket serverConnect;
    private InputStream inputStreamServer;

    public ConnectInputMessage() {
        try {
            serverConnect = new Socket(HOST, PORT);
            inputStreamServer = serverConnect.getInputStream();
        } catch (UnknownHostException e) {
            System.err.println("Cannot find server with name " + HOST);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Something went wrong during getting input stream");
            e.printStackTrace();
        }
    }

    public InputStream getInputStreamServer() {
        return inputStreamServer;
    }

    @Override
    public void run() {
        BufferedReader inReader = new BufferedReader(new InputStreamReader(inputStreamServer));
        String serverMessage = null;

        while (true) {
            try {
                serverMessage = inReader.readLine();
            } catch (IOException e) {
                System.err.println("Error while reading server message");
                e.printStackTrace();
            }

            if (serverMessage != null) {
                System.out.println(serverMessage);
                break;
            }
        }

        BufferedReader inputUser = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out;
        String userMessage;

        while (true) {
            System.out.print("Enter message: ");
            try {
                userMessage = inputUser.readLine();
            } catch (IOException e) {
                System.err.println("Error while reading client message");
                e.printStackTrace();
                continue;
            }
            try {
                out = new PrintWriter(serverConnect.getOutputStream(), true);
            } catch (IOException e) {
                System.err.println("Error while sending client message to server");
                e.printStackTrace();
                continue;
            }

            out.println(userMessage);

            if ("exit".equals(userMessage)) {
                ReceiveMessageFromServer.setState(State.STOP);
                break;
            }
        }
    }
}
