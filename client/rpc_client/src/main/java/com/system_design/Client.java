package com.system_design;

import java.io.IOException;

public class Client {

    public static void main(String[] args) throws IOException {

        RpcClient client = new RpcClient("localhost", 4000);

        String user = client.getUser(42);

        System.out.println(user);
    }

}