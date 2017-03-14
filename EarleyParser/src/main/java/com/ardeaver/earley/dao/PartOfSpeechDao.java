package com.ardeaver.earley.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PartOfSpeechDao {
	private PartOfSpeechManager db;
	
	private static final String GET_PARTS_OF_SPEECH = "SELECT * FROM parts_of_speech";
	private static final String GET_PART_OF_SPEECH_FOR_WORD = "SELECT pos FROM emissions WHERE word=?";
	private static final String GET_PART_OF_SPEECH_PROBABILITY = "SELECT pos, count/(SELECT SUM(count) FROM emissions WHERE word=?) AS prob FROM emissions WHERE word=?";
	
	public PartOfSpeechDao() {
		db = new PartOfSpeechManager();
	}
	
	public List<String> getPartsOfSpeech() {
		List<String> poss = new ArrayList<String>();
		
		Connection c = db.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		if(c != null) {
			try {
				pstmt = c.prepareStatement(GET_PARTS_OF_SPEECH);
				
				rs = pstmt.executeQuery();
				
				String pos;
				
				while(rs.next()) {
					pos = rs.getString("pos");
					poss.add(pos);
				}
			} catch(SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if(c != null) {
						c.close();
					}
					
					if(pstmt != null) {
						pstmt.close();
					}
					
					if(rs != null) {
						rs.close();
					}
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return poss;
	}
	
	public List<String> getPartOfSpeechForWord(String word) {
		List<String> poss = new ArrayList<String>();
		
		Connection c = db.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		if(c != null) {
			try {
				pstmt = c.prepareStatement(GET_PART_OF_SPEECH_FOR_WORD);
				
				pstmt.setString(1, word);
				
				rs = pstmt.executeQuery();
				
				String pos;
				
				while(rs.next()) {
					pos = rs.getString("pos");
					poss.add(pos);
				}
			} catch(SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if(c != null) {
						c.close();
					}
					
					if(pstmt != null) {
						pstmt.close();
					}
					
					if(rs != null) {
						rs.close();
					}
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return poss;
	}
	
	public Map<String, Double> getPartOfSpeechProbabilityForWord(String word) {
		HashMap<String, Double> results = new HashMap<String, Double>();
		
		Connection c = db.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		if(c != null) {
			try {
				pstmt = c.prepareStatement(GET_PART_OF_SPEECH_PROBABILITY);
				
				pstmt.setString(1, word);
				pstmt.setString(2, word);
				
				rs = pstmt.executeQuery();
				
				String pos;
				double probability;
				
				while(rs.next()) {
					pos = rs.getString("pos");
					probability = rs.getDouble("prob");
					results.put(pos, probability);
				}
			} catch(SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if(c != null) {
						c.close();
					}
					
					if(pstmt != null) {
						pstmt.close();
					}
					
					if(rs != null) {
						rs.close();
					}
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return results;
	}
}
