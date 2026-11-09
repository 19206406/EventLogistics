package com.desgroup.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public final class DatabaseConfig {

    private static final String CONFIG_FILE = "database.properties";
    private static Properties properties;

    private DatabaseConfig() {
    }

    private static synchronized Properties properties() {
        if (properties == null)
            properties = loadProperties();

        return properties;
    }

    private static Properties loadProperties() {
        Properties loaded = new Properties();

        try (InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                throw new IllegalStateException(
                        "No se encontró '" + CONFIG_FILE + "'. Debe estar en src/main/resources.");
            }

            try (Reader reader = new InputStreamReader(input, StandardCharsets.UTF_8)) {
                loaded.load(reader);
            }

            return loaded;
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

    public static String getJdbcUrl() {
        return "jdbc:postgresql://" + getHost() + ":" + getPort() + "/" + getDatabaseName();
    }

    public static String getMaintenanceJdbcUrl() {
        return "jdbc:postgresql://" + getHost() + ":" + getPort() + "/postgres";
    }

    public static Properties getHibernateProperties() {
        Properties hibernateProperties = new Properties();

        for (String name : properties().stringPropertyNames()) {
            if (name.startsWith("hibernate.")) {
                hibernateProperties.setProperty(name, properties().getProperty(name));
            }
        }

        hibernateProperties.setProperty("hibernate.connection.url", getJdbcUrl());
        hibernateProperties.setProperty("hibernate.connection.username", getUser());
        hibernateProperties.setProperty("hibernate.connection.password", getPassword());
        hibernateProperties.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");

        return hibernateProperties;
    }

    private static String get(String key) {
        String value = properties().getProperty(key);

        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Falta la propiedad '" + key + "' en " + CONFIG_FILE + ".");
        }

        return value.trim();
    }
}
