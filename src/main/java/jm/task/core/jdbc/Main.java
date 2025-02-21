package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String args[]) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        User user1 = new User("ivan", "ivanov", (byte) 30);
        userService.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        User user2 = new User("petr", "petrov", (byte) 40);
        userService.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        User user3 = new User("sveta", "svetova", (byte) 50);
        userService.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
        User user4 = new User("kata", "katova", (byte) 60);
        userService.saveUser(user4.getName(), user4.getLastName(), user4.getAge());
        List<User> users = new ArrayList<>(userService.getAllUsers());
        for (User user : users) {
            System.out.println(user);
        }
        userService.cleanUsersTable();
        userService.getAllUsers();
    }

}