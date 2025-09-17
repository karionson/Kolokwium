package org.example.server;

import org.example.game.Duel;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Server {
    private static final int PORT = 8080;
    private ServerSocket serverSocket;
    private final List<ClientHandler> clients;
    private Database database;
    private ExecutorService threadPool; // bez tego nie będzie możliwa gra dwóch graczy
    private boolean running;

    public Server() {
        clients = new ArrayList<>();
        database = new Database();
        threadPool = Executors.newCachedThreadPool();
        running = false;
    }

    public void listen() {
        try {
            serverSocket = new ServerSocket(PORT);
            running = true;
            System.out.println("Server started on port " + PORT);
            
            while (running) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("New client connected: " + clientSocket.getInetAddress());
                    
                    ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                    addClient(clientHandler);
                    threadPool.submit(clientHandler::handleClient);
                } catch (IOException e) {
                    if (running) {
                        System.err.println("Error accepting client connection: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        } finally {
            shutdown();
        }
    }

    public synchronized void addClient(ClientHandler client) {
        clients.add(client);
        System.out.println("Klient dodany: " + clients.size());
    }

    public synchronized void removeClient(ClientHandler client) {
        clients.remove(client);
        System.out.println("Klient usunięty: " + clients.size());
    }

    public void sendMessageToAllClients(String message) {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                client.sendMessage(message);
            }
        }
    }

    public Database getDatabase() {
        return database;
    }


    public void challengeToDuel(ClientHandler challenger, String challengeeLogin) {
        if (challenger.getLogin().equals(challengeeLogin)) {
            challenger.sendMessage("Nie możesz grać sam ze sobą");
            return;
        }

        ClientHandler challengee = null;
        synchronized (clients) {
            for (ClientHandler client : clients) {
                if (challengeeLogin.equals(client.getLogin())) {
                    challengee = client;
                    break;
                }
            }
        }

        if (challengee == null) {
            challenger.sendMessage("Player '" + challengeeLogin + "' not found or not online.");
            return;
        }

        // Sprawdzenie czy challenger wykonał ruch
        if (challenger.isDuelling()) {
            challenger.sendMessage("You are already in a duel!");
            return;
        }

        // Sprawdzenie czy challengee wykonuje ruch
        if (challengee.isDuelling()) {
            challenger.sendMessage("Player '" + challengeeLogin + "' is already in a duel!");
            return;
        }

        startDuel(challenger, challengee);
    }


    private void startDuel(ClientHandler challenger, ClientHandler challengee) {
        Duel duel = new Duel(challenger, challengee);

        //W metodzie startDuel() klasy Server wywołaj mutator pola onEnd przekazując mu funkcję, która wywołuje metodę evaluate()
        // pojedynku i w zależności od jej wyniku informuje graczy o remisie, zwycięstwie lub porażce.
        duel.setOnEnd(() -> getResult(challenger, challengee, duel));
        
        // Powiadomienie graczy
        challenger.sendMessage("GAME STARTED with " + challengee.getLogin() + "! Make your move: 'r' for ROCK, 'p' for PAPER, 's' for SCISSORS");
        challengee.sendMessage("GAME STARTED with " + challenger.getLogin() + "! Make your move: 'r' for ROCK, 'p' for PAPER, 's' for SCISSORS");
        
        System.out.println("Duel started between " + challenger.getLogin() + " and " + challengee.getLogin());
    }

    private static Duel.Result getResult(ClientHandler challenger, ClientHandler challengee, Duel duel) {
        //wykonuje evaluate
        Duel.Result result = duel.evaluate();

        if (result == null) {
            challenger.sendMessage("REMIS!");
            challengee.sendMessage("REMIS!");
        } else {
            // Ktoś wygrał
            ClientHandler winner = (ClientHandler) result.winner();
            ClientHandler loser = (ClientHandler) result.loser();

            winner.sendMessage("WYGRALES");
            loser.sendMessage("PRZEGRALES");
        }

        duel.endDuel();
        System.out.println("Duel ended between " + challenger.getLogin() + " and " + challengee.getLogin());
        return result;
    }

    public void shutdown() {
        running = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            synchronized (clients) {
                for (ClientHandler client : clients) {
                    client.disconnect();
                }
                clients.clear();
            }
            threadPool.shutdown();
        } catch (IOException e) {
            System.err.println("Error during shutdown: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\nShutting down server...");
            server.shutdown();
        }));
        
        server.listen();
    }
}
