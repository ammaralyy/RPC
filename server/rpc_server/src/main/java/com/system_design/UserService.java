package com.system_design;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    private static final List<String> primaryUsers = new ArrayList<>();
    private static final List<String> replicaUsers = new ArrayList<>();

    public String getUser(int id) {
        return replicaUsers.stream()
                .filter(user -> user.startsWith(id + ","))
                .findFirst()
                .orElse(null);
    }

    public String getAllUsers() {
        return "Users in database: " + primaryUsers;
    }

    public String createUser(int id, String name) {

        String user = id + "," + name;

        primaryUsers.add(user);

        new Thread(() -> {
            try {
                Thread.sleep(3000);

                replicaUsers.add(user);

                System.out.println("Replica updated: " + replicaUsers);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();

        System.out.println("Primary DB: " + primaryUsers);
        System.out.println("Replica DB: " + replicaUsers);

        return user;
    }

}
