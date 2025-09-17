package org.example.server;

import org.example.game.Gesture;
import org.example.game.Player;

import java.io.*;
import java.net.*;

public class ClientHandler extends Player {
    private Socket clientSocket;
    private Server server;
    private BufferedReader reader;
    private PrintWriter writer;
    private String login;
    private boolean authenticated;
    private boolean running;

    public ClientHandler(Socket clientSocket, Server server) {
        this.clientSocket = clientSocket;
        this.server = server;
        this.authenticated = false;
        this.running = true;
        
        try {
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            disconnect();
        }
    }

    public void handleClient() {
        try {
            if (authenticate()) {
                sendMessage("Authentication successful! Welcome " + login + "!");
                handleClientMessages();
            } else {
                sendMessage("Authentication failed. Disconnecting...");
                disconnect();
            }
        } catch (Exception e) {
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            disconnect();
        }
    }

    private boolean authenticate() {
        try {
            sendMessage("Podaj login:");
            String inputLogin = reader.readLine();
            if (inputLogin == null || inputLogin.trim().isEmpty()) {
                return false;
            }

            sendMessage("Podaj hasło:");
            String inputPassword = reader.readLine();
            if (inputPassword == null || inputPassword.trim().isEmpty()) {
                return false;
            }

            if (server.getDatabase().authenticate(inputLogin, inputPassword)) {
                this.login = inputLogin.trim();
                this.authenticated = true;
                return true;
            }
        } catch (IOException e) {
            System.err.println("Error during authentication: " + e.getMessage());
        }
        return false;
    }

    private void handleClientMessages() {
        try {
            String message;
            while (running && (message = reader.readLine()) != null) {
                System.out.println("Message from " + login + ": " + message);
                
                // Check for disconnect command
                if ("quit".equalsIgnoreCase(message.trim())) {
                    break;
                }
                
                // Handle messages during duel
                if (isDuelling()) {
                    handleDuelMessage(message.trim());
                } else {
                    // Challenge another player to duel
                    server.challengeToDuel(this, message.trim());
                }
            }
        } catch (IOException e) {
            if (running) {
                System.err.println("Error reading from client " + login + ": " + e.getMessage());
            }
        }
    }

    private void handleDuelMessage(String message) {
        // Only accept valid gesture messages during duel
        if ("p".equals(message) || "r".equals(message) || "s".equals(message)) {
            try {
                Gesture gesture = Gesture.fromString(message);
                makeGesture(gesture);
                sendMessage("You played: " + gesture);
            } catch (IllegalArgumentException e) {
                // Should not happen with our validation above, but just in case
                sendMessage("Invalid gesture. Use 'r' for ROCK, 'p' for PAPER, 's' for SCISSORS.");
            }
        }
        // Ignore other messages during duel
    }

    public void sendMessage(String message) {
        if (writer != null) {
            writer.println(message);
        }
    }

    public void disconnect() {
        running = false;
        try {
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.close();
            }
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing client connection: " + e.getMessage());
        } finally {
            server.removeClient(this);
        }
    }

    public String getLogin() {
        return login;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }
}
