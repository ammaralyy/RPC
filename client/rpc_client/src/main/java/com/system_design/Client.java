package com.system_design;

import java.io.IOException;
import java.util.UUID;

public class Client {

    public static void main(String[] args) throws IOException {

        RpcClient server1 = new RpcClient("localhost", 8080);
        RpcClient server2 = new RpcClient("localhost", 8081);

        boolean server1Healthy = true;
        boolean server2Healthy = true;

        for (int i = 0; i < 60; i++) {

            // Check whether previously failed nodes recovered
            if (!server1Healthy && isHealthy(server1)) {
                server1Healthy = true;
                System.out.println("Server 8080 recovered.");
            }

            if (!server2Healthy && isHealthy(server2)) {
                server2Healthy = true;
                System.out.println("Server 8081 recovered.");
            }

            // Both nodes are currently unavailable
            if (!server1Healthy && !server2Healthy) {

                System.out.println("No healthy nodes available. Waiting...");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }

                continue;
            }

            RpcClient primary;
            RpcClient fallback;

            // Simple round robin, while respecting node health
            if (i % 2 == 0 && server1Healthy) {

                primary = server1;
                fallback = server2;

            } else if (server2Healthy) {

                primary = server2;
                fallback = server1;

            } else {

                primary = server1;
                fallback = server2;
            }

            try {

                System.out.println(primary.ping());

            } catch (IOException e) {

                System.out.println("Node failed.");

                if (primary == server1) {
                    server1Healthy = false;
                    System.out.println("Server 8080 marked unhealthy.");
                } else {
                    server2Healthy = false;
                    System.out.println("Server 8081 marked unhealthy.");
                }

                System.out.println("Trying another node...");

                try {

                    if (fallback == server1 && server1Healthy) {

                        System.out.println(server1.ping());

                    } else if (fallback == server2 && server2Healthy) {

                        System.out.println(server2.ping());

                    } else {

                        System.out.println("No healthy fallback node available.");
                    }

                } catch (IOException fallbackError) {

                    if (fallback == server1) {

                        server1Healthy = false;
                        System.out.println("Server 8080 also failed.");

                    } else {

                        server2Healthy = false;
                        System.out.println("Server 8081 also failed.");
                    }

                    System.out.println("No healthy nodes available.");
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

    }

    private static boolean isHealthy(RpcClient server) {

        try {

            server.ping();
            return true;

        } catch (IOException e) {

            return false;
        }
    }

    private static void testGetUserUser(RpcClient client, int id) throws IOException {

        // long start = System.currentTimeMillis();

        String user = client.getUser(id);

        // long end = System.currentTimeMillis();
        // System.out.println("Time taken: " + (end - start) + " ms");

        if (user == null) {
            System.out.println("No response received from server.");
        } else {
            System.out.println(user);
        }

    }

    private static void testCreateUser(RpcClient client, int id, String name) throws IOException {

        String reqId = UUID.randomUUID().toString().substring(0, 8);

        // long start = System.currentTimeMillis();

        String user = client.createUser(reqId, id, name);

        // long end = System.currentTimeMillis();
        // System.out.println("Time taken: " + (end - start) + " ms");

        if (user != null) {
            System.out.println(user);
        } else {
            System.out.println("No response received from server.");
        }
    }

}