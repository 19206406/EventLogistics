package com.desgroup.utils;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.desgroup.models.Coordinator;
import com.desgroup.models.Event;
import com.desgroup.models.EventAssignment;
import com.desgroup.models.Logistic;
import com.desgroup.models.Manager;
import com.desgroup.models.Place;
import com.desgroup.models.Staff;

public final class HibernateUtil {

    private static final SessionFactory SESSION_FACTORY = buildSessionFactory();

    private HibernateUtil() {
    }

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();

            Properties properties = DatabaseConfig.getHibernateProperties();
            for (String name : properties.stringPropertyNames()) {
                configuration.setProperty(name, properties.getProperty(name));
            }

            configuration.addAnnotatedClass(Staff.class);
            configuration.addAnnotatedClass(Coordinator.class);
            configuration.addAnnotatedClass(Manager.class);
            configuration.addAnnotatedClass(Logistic.class);
            configuration.addAnnotatedClass(Place.class);
            configuration.addAnnotatedClass(Event.class);
            configuration.addAnnotatedClass(EventAssignment.class);

            return configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Error al inicializar Hibernate: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    public static void shutdown() {
        if (SESSION_FACTORY != null && !SESSION_FACTORY.isClosed()) {
            SESSION_FACTORY.close();
        }
    }
}
