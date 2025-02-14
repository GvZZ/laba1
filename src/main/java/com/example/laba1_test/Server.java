package com.example.laba1_test;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Serializable {
    public static final ArrayList<Socket> sockets = new ArrayList<>();
    public static void ShowConnectionsList(Socket temp) throws IOException{
        System.out.println("Как минимум нажалось. " + sockets.size());
        ObjectOutputStream out = new ObjectOutputStream(temp.getOutputStream());
        for (Socket socket : sockets) {out.writeObject(socket.getPort());}
        out.close();
    }

    public static void main(String[] args) {
        // Порт, на котором сервер будет слушать подключения
        int port = 8081;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен и ожидает подключения на порту " + port + "...");

            // Бесконечный цикл для принятия новых подключений
            while (true) {
                // Ожидание подключения клиента
                Socket clientSocket = serverSocket.accept();
                System.out.println("Новый клиент подключен: " + clientSocket.getInetAddress());
                synchronized (sockets) {
                    sockets.add(clientSocket);
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при запуске сервера: " + e.getMessage());
        }
    }
}
