package com.system_design;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RpcClient {

    private final String host;
    private final int port;

    public RpcClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getUser(int id) throws IOException {

        Socket socket = new Socket(host, port);

        PrintWriter writer =
                new PrintWriter(
                        socket.getOutputStream(),
                        true
                );

        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()
                        )
                );

        String request = "GET_USER " + id;

        writer.println(request);

        String response = reader.readLine();

        socket.close();

        return response;
    }
    
}
