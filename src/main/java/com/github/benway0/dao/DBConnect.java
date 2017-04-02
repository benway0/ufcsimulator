package com.github.benway0.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Makes a connection to the database.
 */
public class DBConnect {
    
    private static final String DB_HOST = "";
    private static final String DB_USER = "";
    private static final String DB_PASS = "";
    
    private Connection conn;
    private PreparedStatement ps;
    
    /**
     * Establishes the connection.
     */
    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(DB_HOST, DB_USER, DB_PASS);
            
            ps = conn.prepareStatement(
                    "SELECT * FROM users WHERE username = ? AND password = ?");
        } catch (ClassNotFoundException | SQLException ex) {
            ex.getMessage();
        }
    }
    
    /**
     * Checks the database to see if the name and password was authentic.
     * 
     * @param username the input username
     * @param password the input password
     * @return whether authenticated or not
     */
    public boolean auth(String username, String password) {        
        boolean isAuth = false;
        
        try {
            ps.setString(1, username);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();
            
            isAuth = rs.next();
        } catch (SQLException ex) {
            ex.getMessage();
        }
        return isAuth;
    }
}