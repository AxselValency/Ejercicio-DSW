package com.cibertec.Ejercicio_Axsel_Valency;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conennection {
    
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/Ejercicio_2024_A_V";
    private static final String USER = "root";
    private static final String PASSWORD = "mysql";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        try (Connection connection = getConnection()) {
            System.out.println("Conexión exitosa a la base de datos.");

            // Prueba adicional: crear y eliminar una tabla temporal
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("CREATE TEMPORARY TABLE test_table (id INT)");
                System.out.println("Tabla temporal creada exitosamente.");
                stmt.execute("DROP TABLE test_table");
                System.out.println("Tabla temporal eliminada exitosamente.");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error al crear o eliminar la tabla temporal.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al conectarse a la base de datos.");
        }
    }
}
