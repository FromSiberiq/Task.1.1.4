package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {

    public static void main(String args[]) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Sasha", "Smirnov", (byte) 13);
        userService.saveUser("Ivan", "Stalin", (byte) 31);
        userService.saveUser("Nikita", "Ponomaryev", (byte) 39);
        userService.saveUser("Natasha", "Tumanova", (byte) 20);

        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }

}