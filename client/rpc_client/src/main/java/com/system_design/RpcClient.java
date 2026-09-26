package com.system_design;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class RpcClient {

        private final String host;
        private final int port;

        public RpcClient(String host, int port) {
                this.host = host;
                this.port = port;
        }

        public String getUser(int id) throws IOException {

                String response = null;

                try {

                        Socket socket = new Socket(host, port);

                        socket.setSoTimeout(2000);

                        PrintWriter writer = new PrintWriter(
                                        socket.getOutputStream(),
                                        true);

                        BufferedReader reader = new BufferedReader(
                                        new InputStreamReader(
                                                        socket.getInputStream()));

                        String request = "GET_USER " + id;

                        writer.println(request);

                        response = reader.readLine();

                        socket.close();

                } catch (SocketTimeoutException e) {
                        System.out.println("Request timed out after 2 seconds");
                }

                return response;
        }

        public String createUser(String reqId, int id, String name) throws IOException {

                String response = null;

                try {

                        Socket socket = new Socket(host, port);

                        socket.setSoTimeout(2000);

                        PrintWriter writer = new PrintWriter(
                                        socket.getOutputStream(),
                                        true);

                        BufferedReader reader = new BufferedReader(
                                        new InputStreamReader(
                                                        socket.getInputStream()));

                        String request = "CREATE_USER " + reqId + " " + id + " " + name;

                        writer.println(request);

                        response = reader.readLine();

                        socket.close();

                } catch (SocketTimeoutException e) {
                        System.out.println("Request timed out after 2 seconds");
                }
                
                return response;
        }

}
