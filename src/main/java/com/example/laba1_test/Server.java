package com.example.laba1_test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.TreeMap;


public class Server implements Serializable {
    private static final ArrayList<Socket> sockets = new ArrayList<>();
    private static final TreeMap<Integer, ObjectOutputStream> OutputSockets = new TreeMap<>();
    private static final TreeMap<Integer, ObjectInputStream> InputSockets = new TreeMap<>();
    private static void broadcastClientList() {
        synchronized (sockets) {
            for (Socket socket : sockets) {
                try {
                    sendClientList(OutputSockets.get(socket.getPort()));
                } catch (IOException e) {
                    System.err.println("Ошибка при отправке списка клиентов: " + e.getMessage());
                }
            }
        }
    }

    private static void sendClientList(ObjectOutputStream out) throws IOException {
        ArrayList<Integer> socketsTemp = new ArrayList<>();
        synchronized (sockets) {
            for (Socket socket : sockets) {
                socketsTemp.add(socket.getPort());
            }
        }
        out.writeObject(socketsTemp);
        out.flush();
    }

    private static void handleClient(Socket clientSocket) throws IOException {
        try (
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())
        ) {
            OutputSockets.put(clientSocket.getPort(), out);
            InputSockets.put(clientSocket.getPort(), in);

            // Уведомляем всех клиентов о новом подключении
            broadcastClientList();

            // Бесконечный цикл для обработки запросов
            while (true) {
                // Чтение запроса от клиента
                String request = (String) in.readObject();
                System.out.println("Сервер получил запрос от " + clientSocket.getPort() + ": " + request);
                if ("getClients".equals(request)) {
                    broadcastClientList();
                } else if ("exit".equals(request)) {
                    break; // Выход из цикла
                } else {
                    // Неизвестный запрос
                    out.writeObject("Неизвестная команда: " + request);
                    out.flush();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка при обработке клиента " + clientSocket.getPort() + ": " + e.getMessage());
        } finally {
            synchronized (sockets) {
                System.out.println("Зашёл в finally");
                OutputSockets.remove(clientSocket.getPort());
                InputSockets.remove(clientSocket.getPort());
                sockets.remove(clientSocket);
                clientSocket.close();
                System.out.println("Отработал finally");
            }
            System.out.println("Клиент отключен:--------------- " + clientSocket.getPort());
            // Уведомляем всех клиентов об отключении
            broadcastClientList();
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
                new Thread(() -> {
                    try {
                        handleClient(clientSocket);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            }
        } catch (IOException e) {
            System.err.println("Ошибка при запуске сервера: " + e.getMessage());
        }
    }
}
