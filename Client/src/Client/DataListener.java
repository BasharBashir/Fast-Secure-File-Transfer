package Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class DataListener implements Runnable {
    private ServerSocket serverSocket;

    public DataListener(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        System.out.println("ServerSocket: " + serverSocket.toString());

       
    }

    public void run() {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress() +" with socket :"+clientSocket.toString());
                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            } catch (IOException e) {
                System.out.println("Error accepting client connection");
            }
        }
    }
}