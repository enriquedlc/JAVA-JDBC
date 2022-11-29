package com.accesodatos.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {

	// Class instance
	private static DatabaseConnection instance;

	// Connection object
	private Connection connection;

	static String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; // driver JDBC 
	static String JDBC_URL = "jdbc:mysql://localhost:3306/addressbook"; // IP
	static String JDBC_USER = "root"; // user
	static String JDBC_PASS = "1234"; // password
	
	private DatabaseConnection() {
		try {
			connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
			Logger.getLogger(DatabaseConnection.class.getCanonicalName()).log(Level.INFO, "Connected to Database");
		} catch (Exception e) {
			Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, "Database connection Failed", e);
		}
	}
	
	// Singleton Design Pattern
	public static DatabaseConnection getInstance() throws SQLException {
		if (instance == null || instance.getConnection().isClosed()) {
			instance = new DatabaseConnection();
		}
		return instance;
	}
	
	public Connection getConnection() {
		return connection;
	}
}
