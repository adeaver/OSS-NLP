package com.ardeaver.earley.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ardeaver.earley.dto.Prediction;

public class ParsingDao {
	
	private ParsingDBManager db;
	
	private static final String FIND_PREDICTIONS = "SELECT * FROM parses WHERE root=?";
	
	public ParsingDao() {
		db = new ParsingDBManager();
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
	
	private List<String> convertArrayToList(String[] array) {
		List<String> list = new ArrayList<String>();
		
		for(String s : array) {
			list.add(s);
		}
		
		return list;
	}
}
