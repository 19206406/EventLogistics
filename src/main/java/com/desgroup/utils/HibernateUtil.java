package com.desgroup.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.desgroup.models.Logistic;

public class HibernateUtil {
    private static final String CONNECTION_STRING = "jdbc:postgresql://localhost:5432/event-logistic-db?user=postgres&password=admin123";

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        Configuration config = new Configuration();

        config.setProperty("hibernate.connection.url", CONNECTION_STRING);
        config.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        config.setProperty("hibernate.show_sql", "true");
        config.setProperty("hibernate.format_sql", "true");

        // aquí se establecen los modelos para la base de datos
        config.addAnnotatedClass(Logistic.class);

        return config.buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
