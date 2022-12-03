package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReceiveMessageFromServer implements Runnable {
    private final InputStream inputStreamServer;
    private static State state;

    public ReceiveMessageFromServer(InputStream inputStream) {
        inputStreamServer = inputStream;
        state = State.RUN;
    }

    public static State getState() {
        return state;
    }

    public static void setState(State state) {
        ReceiveMessageFromServer.state = state;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(inputStreamServer))) {
            String serverMessage;

            while (state == State.RUN) {
                serverMessage = in.readLine();
                if (serverMessage != null) {
                    System.out.print("\n" + serverMessage + "\nEnter message: ");
                }
            }
        } catch (IOException e) {
            System.err.println("Error while getting server stream");
            e.printStackTrace();
        }
    }
}
