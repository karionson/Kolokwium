import game.Duel;
import game.Gesture;
import server.Client;
import server.ClientHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private final int port = 8000;
    private ServerSocket server;
    private List<ClientHandler> clients;

    public Server() {
        try {
            this.server = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.clients = new ArrayList<>();
    }

    void listen() {
        try (Socket socket = server.accept()) {
            while (true) {
                ClientHandler clientHandler = new ClientHandler(socket);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println("Login: ");
                String login = in.readLine();
                while(login !=null) {
                    out.println(login);
                }
                String password = in.readLine();
                out.println("Passowrd: ");
                while(password !=null) {
                    out.println(password);
                }
                if(!(clientHandler.authenticate(login, password))) {
                    disconnet(clientHandler);
                } else {
                    clientHandler.setLoginAndPassword(login, password);
                }
                this.clients.add(clientHandler);
                this.clients.remove(clientHandler);

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void challengeToDuel(ClientHandler challenger, String challengeeLogin) throws IOException {
        for(ClientHandler clientHandler : this.clients) {
            if(!challenger.isDuelling()) {
                if (clientHandler.getLogin().equals(challengeeLogin)) {
                    if(!clientHandler.isDuelling()) {
                        startDuel(challenger, clientHandler);
                    } else {
                        challenger.getOut().println("Gracz o podanym loginie się pojedynkuje");
                    }
                } else {
                    challenger.getOut().println("Nie ma gracza o podanym loginie");
                }
            } else {
                challenger.getOut().println("Gracz, ktorego probujesz wyzwac sie aktualnie pojedynkuje!");

            }
        }

    }

    private void startDuel(ClientHandler challenger, ClientHandler challengee) throws IOException {
        Duel duel = new Duel(challenger, challengee);
       challenger.getOut().println("Duel został rozpoczęty");
       challengee.getOut().println("Duel zostal rozpoczety");

       String stringGestureChallanger = challenger.getIn().readLine();

       if(stringGestureChallanger.equals("r") || stringGestureChallanger.equals("s") ||  stringGestureChallanger.equals("p")) {
           Gesture.fromString(stringGestureChallanger);
       }

        duel.mutate(_ -> duel.evaluate());
        Duel.Result result = duel.getOnEnd().apply(null);
        if(result.winner().equals(challenger)) {
            challenger.getOut().println("Wygrales");
            challengee.getOut().println("Przegrales");
        }
        else if (result.winner().equals(challengee)) {
            challengee.getOut().println("Wygrales");
            challenger.getOut().println("Przegrales");
        }
        else {
            challengee.getOut().println("Remis");
            challenger.getOut().println("Remis");
        }

    }


    public void send(String message) {

    }

    public static void disconnet(ClientHandler client) {
        try {
            client.getSocket().close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.listen();
    }
}
