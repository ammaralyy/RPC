package com.system_design;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {

        int port = 4000;

        ServerSocket serverSocket = new ServerSocket(port);

        System.out.println("Server started on port " + port);
        System.out.println("Waiting for client...");

        Socket clientSocket = serverSocket.accept();

        System.out.println("Client connected!");

        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(
                                clientSocket.getInputStream()
                        )
                );

        PrintWriter writer =
                new PrintWriter(
                        clientSocket.getOutputStream(),
                        true
                );

        String request = reader.readLine();

        System.out.println("Received: " + request);

        // Example: GET_USER 42
        String[] parts = request.split(" ");

        String method = parts[0];
        int userId = Integer.parseInt(parts[1]);

        UserService userService = new UserService();

        if (method.equals("GET_USER")) {

            String result = userService.getUser(userId);

            System.out.println("Method result: " + result);

            writer.println(result);
        }

        clientSocket.close();
        serverSocket.close();
    }

}