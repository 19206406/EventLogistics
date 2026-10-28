package com.desgroup.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * Lee la configuración de {@code database.properties} (carpeta
 * {@code src/main/resources}) y construye las URLs y propiedades que necesitan
 * Flyway y Hibernate.
 *
 * Al centralizar aquí la configuración, la contraseña y el nombre de la base de
 * datos solo se cambian en un único archivo.
 */
public final class DatabaseConfig {

    private static final String CONFIG_FILE = "database.properties";
    private static final Properties PROPERTIES = loadProperties();

    private DatabaseConfig() {
        // Clase de utilidades: no se instancia.
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();

        try (InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                throw new IllegalStateException(
                        "No se encontró '" + CONFIG_FILE + "'. Debe estar en src/main/resources.");
            }

            try (Reader reader = new InputStreamReader(input, StandardCharsets.UTF_8)) {
                properties.load(reader);
            }

            return properties;
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo leer '" + CONFIG_FILE + "'.", e);
        }
    }

    public static String getHost() {
        return get("db.host");
    }

    public static String getPort() {
        return get("db.port");
    }

    public static String getDatabaseName() {
        return get("db.name");
    }

    public static String getUser() {
        return get("db.user");
    }

    public static String getPassword() {
        return get("db.password");
    }

    /**
     * URL de la base de datos de la aplicación.
     * Ejemplo: jdbc:postgresql://localhost:5432/event-logistic-db
     */
    public static String getJdbcUrl() {
        return "jdbc:postgresql://" + getHost() + ":" + getPort() + "/" + getDatabaseName();
    }

    /**
     * URL de la base de datos de mantenimiento {@code postgres}.
     * Se usa solo para poder ejecutar CREATE DATABASE, porque PostgreSQL no
     * permite crear una base de datos desde dentro de sí misma.
     */
    public static String getMaintenanceJdbcUrl() {
        return "jdbc:postgresql://" + getHost() + ":" + getPort() + "/postgres";
    }

    /**
     * Propiedades que se le pasan a Hibernate.
     * Copia todas las claves que empiezan por "hibernate." del archivo y añade
     * la conexión, para no repetir usuario/contraseña en dos sitios.
     */
    public static Properties getHibernateProperties() {
        Properties hibernateProperties = new Properties();

        for (String name : PROPERTIES.stringPropertyNames()) {
            if (name.startsWith("hibernate.")) {
                hibernateProperties.setProperty(name, PROPERTIES.getProperty(name));
            }
        }

        hibernateProperties.setProperty("hibernate.connection.url", getJdbcUrl());
        hibernateProperties.setProperty("hibernate.connection.username", getUser());
        hibernateProperties.setProperty("hibernate.connection.password", getPassword());
        hibernateProperties.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");

        return hibernateProperties;
    }

    private static String get(String key) {
        String value = PROPERTIES.getProperty(key);

        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Falta la propiedad '" + key + "' en " + CONFIG_FILE + ".");
        }

        return value.trim();
    }
}
