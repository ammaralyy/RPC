package com.system_design;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {

        private static final Map<String, String> processedRequests = new HashMap<>();

        public static void main(String[] args) throws IOException, InterruptedException {

                int port = Integer.parseInt(args[0]);

                ServerSocket serverSocket = new ServerSocket(port);

                System.out.println("Server started on port " + port);

                while (true) {

                        System.out.println("Waiting for client...");

                        Socket clientSocket = serverSocket.accept();

                        System.out.println("Client connected!");

                        try {

                                BufferedReader reader = new BufferedReader(
                                                new InputStreamReader(clientSocket.getInputStream()));

                                PrintWriter writer = new PrintWriter(
                                                clientSocket.getOutputStream(),
                                                true);

                                String request;

                                while ((request = reader.readLine()) != null) {

                                        System.out.println("Received: " + request);

                                        String[] parts = request.split(" ");

                                        String method = parts[0];

                                        UserService userService = new UserService();

                                        if (method.equals("PING")) {

                                                String result = "Server running on port " + port;

                                                System.out.println("Method result: " + result);

                                                writer.println(result);

                                        } else if (method.equals("GET_USER")) {

                                                int userId = Integer.parseInt(parts[1]);
                                                String result = userService.getUser(userId);

                                                System.out.println("Method result: " + result);

                                                // Thread.sleep(5000);

                                                writer.println(result);

                                        } else if (method.equals("CREATE_USER")) {

                                                String reqId = parts[1];

                                                int userId = Integer.parseInt(parts[2]);
                                                String name = parts[3];

                                                if (processedRequests.containsKey(reqId)) {

                                                        System.out.println("Duplicate request detected: " + reqId);

                                                        String previousResult = processedRequests.get(reqId);

                                                        System.out.println(userService.getAllUsers());

                                                        writer.println(previousResult);

                                                        continue;
                                                }

                                                String result = userService.createUser(userId, name);

                                                processedRequests.put(reqId, result);

                                                System.out.println("Method result: " + result);

                                                Thread.sleep(2000);

                                                writer.println(result);
                                        }
                                }

                        } catch (IOException e) {

                                System.out.println("Client connection lost.");

                        } finally {

                                clientSocket.close();
                        }
                }

        }
}