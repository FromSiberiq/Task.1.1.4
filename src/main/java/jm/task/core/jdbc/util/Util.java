package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class Util {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/task.1.1.3";
    private static final String DB_USER = "postgres";
    private static final String DB_PASS = "123qwe";

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "org.postgresql.Driver");
                settings.put(Environment.URL, DB_URL);
                settings.put(Environment.USER, DB_USER);
                settings.put(Environment.PASS, DB_PASS);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
                settings.put(Environment.HBM2DDL_AUTO, "update");
                settings.put(Environment.SHOW_SQL, "true");

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            } catch (Exception ex) {
                ex.printStackTrace();
                throw new ExceptionInInitializerError("Ошибка инициализации SessionFactory: " + ex.getMessage());
            }
        }
        return sessionFactory;
    }

}
