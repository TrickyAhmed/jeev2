package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure(); // Load hibernate.cfg.xml configuration
            configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/userdb?serverTimezone=UTC");
            // You may need to replace 'your_database' with the name of your MySQL database

            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
