package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users " +
                "(id SERIAL PRIMARY KEY, " +
                "name VARCHAR(100), " +
                "lastName VARCHAR(100), " +
                "age INT)";
        executeUpdate(sql, "Таблица создана.");
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        executeUpdate(sql, "Таблица удалена.");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
            System.out.println("Пользователь добавлен: " + name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
                System.out.println("Пользователь удален: " + id);
            } else {
                System.out.println("Пользователь не найден.");
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = getSessionFactory().openSession()) {
            List<User> users = session.createQuery("FROM User", User.class).list();
            System.out.println("Пользователи получены успешно.");
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void cleanUsersTable() {
        executeUpdate("DELETE FROM users", "Таблица очищена.");
    }

    private void executeUpdate(String sql, String successMessage) {
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            transaction.commit();
            System.out.println(successMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
