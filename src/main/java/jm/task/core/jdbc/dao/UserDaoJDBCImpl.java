package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection;

    public UserDaoJDBCImpl() {
        try {
            this.connection = Util.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createUsersTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                "id SERIAL PRIMARY KEY," +
                "name VARCHAR(100)," +
                "lastName VARCHAR(100) UNIQUE," +
                "age SMALLINT" +
                ")";

        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
            System.out.println("Users table created");
        } catch (SQLException c) {
            System.out.println("Users table creation failed");
        }
    }

    public void dropUsersTable() {
        String dropTableSQL = "DROP TABLE IF EXISTS users";
        try (Statement statement = connection.createStatement()) {
            statement.execute(dropTableSQL);
            System.out.println("Table 'users' dropped successfully.");
        } catch (SQLException c) {
            System.out.println("Table 'users' drop failed");
        }
    }

    public void saveUser(String user_name, String user_lastName, byte user_age) {
        String insertSQL = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, user_name);
            preparedStatement.setString(2, user_lastName);
            preparedStatement.setByte(3, user_age);
            preparedStatement.executeUpdate();
        } catch (SQLException c) {
            System.out.println("Users table insertion failed");
        }
    }

    public void removeUserById(long id) {
        String deleteSQL = "DELETE FROM users WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("User with ID " + id + " deleted successfully.");
        } catch (SQLException c) {
            System.out.println("Users table deletion failed");
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String selectSQL = "SELECT * FROM users";
        try (ResultSet set = connection.prepareStatement(selectSQL).executeQuery()) {
            while (set.next()) {
                User user = new User();
                user.setId(set.getLong("id"));
                user.setName(set.getString("name"));
                user.setLastName(set.getString("lastName"));
                user.setAge(set.getByte("age"));
                users.add(user);
            }
        } catch (SQLException c) {
            System.out.println("Users table reading failed");
        }
        return users;
    }

    public void cleanUsersTable() {
        String truncateTableSQL = "TRUNCATE TABLE users";
        try (Statement statement = connection.createStatement()) {
            statement.execute(truncateTableSQL);
            System.out.println("Users table truncated");
        } catch (SQLException c) {
            System.out.println("Users table truncation failed");
        }
    }
}