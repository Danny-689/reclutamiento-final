package main.java.edu.kinal.reclutamiento.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/reclutamiento_db_in4bm";
    private static final String USER = "IN4BM";
    private static final String PASSWORD = "";

    public static Connection getConnection()
            throws SQLException {

        return DriverManager.getConnection(
                URL, USER, PASSWORD
        );
    }
}
