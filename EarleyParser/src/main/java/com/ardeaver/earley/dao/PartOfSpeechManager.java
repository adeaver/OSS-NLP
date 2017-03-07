package com.ardeaver.earley.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PartOfSpeechManager {

	private static final String HOST = "jdbc:mysql://localhost/tagger";
	private static final String USER = "simplePOS";
	private static final String PWD = "alea";
	
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
