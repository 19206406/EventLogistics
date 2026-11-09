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

import jakarta.persistence.PersistenceException;

public final class HibernateUtil {

    private static SessionFactory sessionFactory;

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
        } catch (PersistenceException ex) {
            throw new IllegalStateException("No se pudo inicializar Hibernate. "
                    + "Revisa que las entidades coincidan con el esquema de la base de datos.", ex);
        }
    }

    public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null)
            sessionFactory = buildSessionFactory();

        return sessionFactory;
    }

    public static synchronized void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}
