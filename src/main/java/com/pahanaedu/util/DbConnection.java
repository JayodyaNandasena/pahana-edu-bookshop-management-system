package com.pahanaedu.util;

import java.io.InputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnection {

	private static String DRIVER;
	private static String URL;
	private static String USERNAME;
	private static String PASSWORD;

	private static volatile DbConnection instance;
	private Connection connection;

	// Load the properties once
	static {
		Properties props = new Properties();
		try (InputStream in = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("database.properties")) {
			if (in == null) {
				throw new IllegalStateException("database.properties not found on classpath");
			}
			props.load(in);

			DRIVER = props.getProperty("db.driver");
			URL = props.getProperty("db.url");
			USERNAME = props.getProperty("db.username");
			PASSWORD = props.getProperty("db.password");

			if (DRIVER == null || URL == null || USERNAME == null || PASSWORD == null) {
				throw new IllegalStateException("Missing one or more DB properties");
			}
		} catch (IOException ex) {
			throw new RuntimeException("Failed to load database.properties", ex);
		}
	}

	private DbConnection() {
		try {
			Class.forName(DRIVER);
			this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			throw new RuntimeException("Cannot initialize DB connection", e);
		}
	}

	public static DbConnection getInstance() {
		if (instance == null) {
			synchronized (DbConnection.class) {
				if (instance == null) {
					instance = new DbConnection();
				}
			}
		}
		return instance;
	}

	public Connection getConnection() {
		try {
			if (connection == null || connection.isClosed()) {
				// re-open if it was closed
				connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			}
		} catch (SQLException e) {
			throw new RuntimeException("Failed to (re)open DB connection", e);
		}
		return connection;
	}
}