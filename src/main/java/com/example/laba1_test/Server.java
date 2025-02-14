package com.example.laba1_test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Serializable {
    private static final ArrayList<Socket> sockets = new ArrayList<>();

    private static void handleClient(Socket clientSocket) {
        try (
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())
        ) {
            // Бесконечный цикл для обработки запросов
            while (true) {
                // Чтение запроса от клиента
                String request = (String) in.readObject();
                System.out.println("Сервер получил запрос от " + clientSocket.getInetAddress() + ": " + request);

                if ("getClients".equals(request)) {
                    // Создаем список ClientInfo для передачи
                    ArrayList<Integer> clientInfos = new ArrayList<>();
                    synchronized (sockets) {
                        for (Socket socket : sockets) {
                            clientInfos.add(socket.getPort());
                        }
                    }
                    // Отправляем список клиентов
                    out.writeObject(clientInfos);
                    out.flush();
                } else if ("exit".equals(request)) {
                    // Обработка запроса на отключение
                    System.out.println("Клиент " + clientSocket.getPort() + " запросил отключение.");
                    break; // Выход из цикла
                } else {
                    // Неизвестный запрос
                    out.writeObject("Неизвестная команда: " + request);
                    out.flush();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка при обработке клиента " + clientSocket.getInetAddress() + ": " + e.getMessage());
        } finally {
            try {
                // Удаляем сокет из списка
                synchronized (sockets) {
                    sockets.remove(clientSocket);
                }
                // Закрываем соединение
                clientSocket.close();
                System.out.println("Клиент отключен: " + clientSocket.getInetAddress());
            } catch (IOException e) {
                System.err.println("Ошибка при закрытии сокета: " + e.getMessage());
            }
        }
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
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            System.err.println("Ошибка при запуске сервера: " + e.getMessage());
        }
    }
}
