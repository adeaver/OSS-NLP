package com.ardeaver.deconjugator.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class TokenDao {
	private static final String WORD_QUERY = "SELECT * FROM unigrams WHERE word = ?";
	private static final String COUNT_QUERY = "SELECT COUNT(*) c FROM unigrams";
	
	public int getCountForWord(String word) {
		DatabaseManager db = new DatabaseManager();
		Connection conn = db.getConnection();
		String[] columns = {"count"};
		HashMap<String, Object> countForWord = new HashMap<String, Object>();
		
		if(conn != null) {
			try {
				countForWord = db.executeQuery(conn, columns, WORD_QUERY, word);
			} catch(SQLException e) {
				e.printStackTrace();
			} finally {
				closeResources(conn);
			}
		}
		
		return ((Integer) countForWord.getOrDefault("count", -1));
	}
	
	public int getTotalTokenCount() {
		DatabaseManager db = new DatabaseManager();
		Connection conn = db.getConnection();
		String[] columns = {"c"};
		HashMap<String, Object> results = new HashMap<String, Object>();
		
		if(conn != null) {
			try {
				results = db.executeQuery(conn, columns, COUNT_QUERY);
			} catch(SQLException e) {
				e.printStackTrace();
			} finally {
				closeResources(conn);
			}
		}
		
		return ((Integer) results.getOrDefault("c", -1));
	}
	
	private static void closeResources(Connection c) {
		try {
			if(c != null) {
				c.close();
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
