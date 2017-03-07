package com.ardeaver.earley.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ParsingDBManager {
	
	private static final String HOST = "jdbc:mysql://localhost:3306/earley";
	private static final String USER = "earley";
	private static final String PWD = "parsing";
	
	public Connection getConnection() {
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(HOST, USER, PWD);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
}
