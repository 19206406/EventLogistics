package com.desgroup.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.output.MigrateResult;

public final class DatabaseInitializer {

    private DatabaseInitializer() {
    }

    public static void initialize() {
        createDatabaseIfNotExists();
        runMigrations();
    }

    public static void main(String[] args) {
        initialize();
        HibernateUtil.getSessionFactory();
        System.out.println("Base de datos lista y entidades validadas correctamente.");
        HibernateUtil.shutdown();
    }

    private static void createDatabaseIfNotExists() {
        String databaseName = DatabaseConfig.getDatabaseName();
        String user = DatabaseConfig.getUser();
        String password = DatabaseConfig.getPassword();

        try (Connection connection = DriverManager.getConnection(
                DatabaseConfig.getMaintenanceJdbcUrl(), user, password)) {

            if (databaseExists(connection, databaseName)) {
                System.out.println("Base de datos '" + databaseName + "' ya existe. No se crea de nuevo.");
                return;
            }

            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("CREATE DATABASE " + quoteIdentifier(databaseName));
                System.out.println("Base de datos '" + databaseName + "' creada correctamente.");
            }

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "No se pudo crear o verificar la base de datos '" + databaseName + "'.\n"
                            + "Revisa que PostgreSQL esté corriendo en " + DatabaseConfig.getHost() + ":"
                            + DatabaseConfig.getPort() + ", que el usuario '" + user
                            + "' y la contraseña sean correctos, y que el usuario tenga permiso CREATEDB.",
                    e);
        }
    }

    private static boolean databaseExists(Connection connection, String databaseName) throws SQLException {
        String sql = "SELECT 1 FROM pg_database WHERE datname = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, databaseName);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    private static void runMigrations() {
        System.out.println("Aplicando migraciones de Flyway...");

        Flyway flyway = Flyway.configure()
                .dataSource(DatabaseConfig.getJdbcUrl(), DatabaseConfig.getUser(), DatabaseConfig.getPassword())
                .locations("classpath:db/migration")
                .load();

        MigrateResult result = flyway.migrate();

        if (result.migrationsExecuted == 0) {
            System.out.println("No había migraciones pendientes. Esquema actualizado.");
        } else {
            System.out.println("Migraciones ejecutadas: " + result.migrationsExecuted);
        }
    }

    private static String quoteIdentifier(String identifier) {
        return "\"" + identifier.replace("\"", "\"\"") + "\"";
    }
}
