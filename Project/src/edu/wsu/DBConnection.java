package edu.wsu;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private String SQL_DRIVER = "com.mysql.jdbc.Driver";
    private String DATABASE_URL = "jdbc:mysql://localhost:3306/sampledb";
    private String USER = "john";
    private String PASSWORD = "pass1234";

    private Connection _dbConnection;

    public DBConnection() {
        try{
            Class.forName(SQL_DRIVER);

            _dbConnection = DriverManager.getConnection(
                    DATABASE_URL,
                    USER,
                    PASSWORD);

        } catch(Exception e) {
            System.out.println(e);
            _dbConnection = null;
        }
    }

    Connection getConnection() {
        return _dbConnection;
    }

    void closeConnection() {
        if (_dbConnection != null) {
            try {
                _dbConnection.close();
            } catch(Exception e) {
                System.out.println(e);
            }
            _dbConnection = null;
        }
    }
}
