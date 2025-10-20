package com.java.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionManager {
	private static final String URL="jdbc:oracle:thin:@//localhost:1521/ORCLPDB1";

	private static final String USER_NAME="kingmu";
	private static final String PASSWORD = "rla1234";

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER_NAME, PASSWORD);
	}
}
