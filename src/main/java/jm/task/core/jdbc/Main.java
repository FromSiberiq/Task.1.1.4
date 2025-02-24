package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {

    public static void main(String args[]) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser ("Sasha", "Smirnov", (byte) 13);
        userService.saveUser ("Ivan", "Stalin", (byte) 31);
        userService.saveUser ("Nikita", "Ponomaryev", (byte) 39);
        userService.saveUser ("Natasha", "Tumanova", (byte) 20);

        List<User> users = userService.getAllUsers();
        System.out.println("Все пользователи из базы данных:");
        for (User  user : users) {
            System.out.println(user);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }

}