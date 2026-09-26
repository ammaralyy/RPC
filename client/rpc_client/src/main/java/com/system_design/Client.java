package com.system_design;

import java.io.IOException;
import java.util.UUID;

public class Client {

    public static void main(String[] args) throws IOException {

        RpcClient client = new RpcClient("localhost", 8080);

        Client.testCreateUser(client);
        Client.testGetUserUser(client);

    }

    private static void testGetUserUser(RpcClient client) throws IOException {
        
        long start = System.currentTimeMillis();

        String user = client.getUser(1);

        long end = System.currentTimeMillis();

        System.out.println("Time taken: " + (end - start) + " ms");

        if (user == null) {
            System.out.println("No response received from server.");
            user = client.getUser(42);
        }

        if (user != null) {
            System.out.println(user);
        } else {
            System.out.println("No response received from server.");
        }
    }

    private static void testCreateUser(RpcClient client) throws IOException {

        String reqId = UUID.randomUUID().toString().substring(0, 8);
        
        long start = System.currentTimeMillis();

        String user = client.createUser(reqId, 1, "Ammar");

        long end = System.currentTimeMillis();

        System.out.println("Time taken: " + (end - start) + " ms");

        if (user == null) {
            System.out.println("No response received from server.");
            user = client.createUser(reqId, 1, "Ammar");
        }

        if (user != null) {
            System.out.println(user);
        } else {
            System.out.println("No response received from server.");
        }
    }

}