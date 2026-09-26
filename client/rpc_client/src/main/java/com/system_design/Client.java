package com.system_design;

import java.io.IOException;
import java.util.UUID;

public class Client {

    public static void main(String[] args) throws IOException {

        RpcClient client = new RpcClient("localhost", 8080);

        int id = 1;

        Client.testCreateUser(client);
        Client.testGetUserUser(client, id);

    }

    private static void testGetUserUser(RpcClient client, int id) throws IOException {

        String user = null;

            // long start = System.currentTimeMillis();

            user = client.getUser(id);

            // long end = System.currentTimeMillis();
            // System.out.println("Time taken: " + (end - start) + " ms");

            if (user == null) {
                System.out.println("No response received from server.");
            } else {
                System.out.println(user);
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