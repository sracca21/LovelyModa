package com.lovelymoda.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/dblovelymoda";
    private static final String USER = "root"; // usuario
    private static final String PASS = "@#RootMySQL101"; // password

    public Connection getConnection() throws SQLException {        
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
