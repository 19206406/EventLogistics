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

/**
 * Punto único de acceso a Hibernate.
 *
 * La conexión y las opciones ya no están escritas aquí: se leen de
 * {@code database.properties} a través de {@link DatabaseConfig}.
 *
 * Con {@code hibernate.hbm2ddl.auto=validate}, Hibernate solo comprueba que las
 * entidades coincidan con las tablas creadas por Flyway; no crea ni borra nada.
 */
public final class HibernateUtil {

    private static final SessionFactory SESSION_FACTORY = buildSessionFactory();

    private HibernateUtil() {
        // Clase de utilidades: no se instancia.
    }

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();

            Properties properties = DatabaseConfig.getHibernateProperties();
            for (String name : properties.stringPropertyNames()) {
                configuration.setProperty(name, properties.getProperty(name));
            }

            // Aquí se registran las entidades que Hibernate debe conocer.
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

    /**
     * Cierra la SessionFactory y libera las conexiones.
     * Se llama desde un shutdown hook al terminar la aplicación.
     */
    public static void shutdown() {
        if (SESSION_FACTORY != null && !SESSION_FACTORY.isClosed()) {
            SESSION_FACTORY.close();
        }
    }
}
