package com.ardeaver.earley.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ardeaver.earley.dto.Prediction;
import com.ardeaver.earley.dto.ProbabilisiticPrediction;

public class ParsingDao {
	
	private ParsingDBManager db;
	
	private static final String FIND_PREDICTIONS = "SELECT * FROM parses WHERE root=?";
	private static final String FIND_ALL_PREDICTIONS = "SELECT * FROM parses";
	
	private static final String FIND_PREDICITIONS_PROBABILITY = " SELECT root, parse, count/(SELECT SUM(count) FROM parses WHERE root=?) AS prob FROM parses WHERE root=?";
	
	public ParsingDao() {
		db = new ParsingDBManager();
	}
	
	public List<Prediction> getAllPredictions() {
		List<Prediction> predictions = new ArrayList<Prediction>();
		
		Connection c = db.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		if(c != null) {
			try {
				pstmt = c.prepareStatement(FIND_ALL_PREDICTIONS);
				
				rs = pstmt.executeQuery();
				
				String root;
				List<String> parses;
				int count;
				
				while(rs.next()) {
					parses = convertArrayToList(rs.getString("parse").split("\\s+"));
					count = rs.getInt("count");
					root = rs.getString("root");
					predictions.add(new Prediction(root, parses, count));
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
		
		return predictions;
	}
	
	public List<Prediction> getPredictionsForRoot(String root) {
		List<Prediction> predictions = new ArrayList<Prediction>();
		
		Connection c = db.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		if(c != null) {
			try {
				pstmt = c.prepareStatement(FIND_PREDICTIONS);
				
				pstmt.setString(1, root);
				rs = pstmt.executeQuery();
				
				List<String> parses;
				int count;
				
				while(rs.next()) {
					parses = convertArrayToList(rs.getString("parse").split("\\s+"));
					count = rs.getInt("count");
					predictions.add(new Prediction(root, parses, count));
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
		
		return predictions;
	}
	
	public List<ProbabilisiticPrediction> getProbabilisiticPredictionsForRoot(String root) {
		List<ProbabilisiticPrediction> predictions = new ArrayList<ProbabilisiticPrediction>();
		
		Connection c = db.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		if(c != null) {
			try {
				pstmt = c.prepareStatement(FIND_PREDICITIONS_PROBABILITY);
				
				pstmt.setString(1, root);
				pstmt.setString(2, root);
				rs = pstmt.executeQuery();
				
				List<String> parses;
				double count;
				
				while(rs.next()) {
					parses = convertArrayToList(rs.getString("parse").split("\\s+"));
					count = rs.getInt("prob");
					predictions.add(new ProbabilisiticPrediction(root, parses, count));
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
		
		return predictions;
	}
	
	private List<String> convertArrayToList(String[] array) {
		List<String> list = new ArrayList<String>();
		
		for(String s : array) {
			list.add(s);
		}
		
		return list;
	}
}
