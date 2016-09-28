package com.ardeaver.deconjugator.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;

public class DatabaseManager {
	private static final String HOST = "jdbc:mysql://localhost:3306/spanishgrams";
	private static final String USER = "ngrams";
	private static final String PWD = "ngramspass";
	
	public Connection getConnection() {
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(HOST, USER, PWD);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public HashMap<String, Object> executeQuery(Connection conn, String[] columns, String query, String... params) 
					throws SQLException {
		ResultSet rs = null;
		PreparedStatement pstmt = conn.prepareStatement(query);
		HashMap<String, Object> result = new HashMap<String, Object>();
		int index = 1;
		
		if(params.length > 0) {
			for(String param : params) {
				pstmt.setString(index, param);
				index++;
			}
		}
		
		rs = pstmt.executeQuery();
		
		if(rs.first()) {
			for(String column : columns) {
				result.put(column, rs.getObject(column));
			}
		}
		
		rs.close();
		pstmt.close();
		
		return result;
	}
}
