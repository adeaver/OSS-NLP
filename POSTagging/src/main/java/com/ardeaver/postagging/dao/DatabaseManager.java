package com.ardeaver.postagging.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
	private static final String HOST = "jdbc:mysql://localhost/pos_tagging";
	private static final String USER = "pos_tagger";
	private static final String PWD = "hmms";
	
	public Connection getConnection() {
		Connection conn = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(HOST, USER, PWD);
		} catch(SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conn;
	}
}
