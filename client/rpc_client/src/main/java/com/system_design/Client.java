package com.system_design;

import java.io.IOException;

public class Client {

    public static void main(String[] args) throws IOException {

        RpcClient client = new RpcClient("localhost", 8080);

        long start = System.currentTimeMillis();

        String user = client.getUser(42);

        long end = System.currentTimeMillis();

        System.out.println("Time taken: " + (end - start) + " ms");
        
        System.out.println(user);
    }

}