package server;

import game.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Player {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String login;
    private String password;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void setLoginAndPassword(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Socket getSocket() {
        return socket;
    }

    public PrintWriter getOut() {
        return out;
    }

    public BufferedReader getIn() {
        return in;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
