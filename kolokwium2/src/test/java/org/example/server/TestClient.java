package org.example.server;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TestClient {
    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORT);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to server!");
            System.out.println("Instructions:");
            System.out.println("1. First, you'll be asked for login and password");
            System.out.println("2. After authentication, send another player's login to challenge them");
            System.out.println("3. During duel, send 'r' for ROCK, 'p' for PAPER, 's' for SCISSORS");
            System.out.println("4. Type 'quit' to disconnect");
            System.out.println();

            // Thread to read messages from server
            Thread readerThread = new Thread(() -> {
                try {
                    String message;
                    while ((message = reader.readLine()) != null) {
                        System.out.println("Server: " + message);
                    }
                } catch (IOException e) {
                    System.out.println("Connection to server lost.");
                }
            });
            readerThread.start();

            // Main thread to send messages to server
            String input;
            while ((input = scanner.nextLine()) != null) {
                writer.println(input);
                if ("quit".equalsIgnoreCase(input)) {
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
        }
    }
}
