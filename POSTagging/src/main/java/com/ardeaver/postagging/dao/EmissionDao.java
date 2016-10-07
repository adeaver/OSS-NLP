package com.ardeaver.postagging.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmissionDao {
	private static final String GET_EMISSION_FOR_POS = "SELECT * FROM emissions WHERE word=? AND pos=?";
	private static final String GET_TOTAL_EMISSIONS = "SELECT SUM(count) sum FROM emissions WHERE word=?";
	
	private DatabaseManager dbm;
	
	public EmissionDao() {
		dbm = new DatabaseManager();
	}
	
	public int getEmissionsForPOS(String word, String pos) {
		int emissions = 0;
		
		Connection c = dbm.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		if(c != null) {
			try {
				pstmt = c.prepareStatement(GET_EMISSION_FOR_POS);
				pstmt.setString(1, word);
				pstmt.setString(2, pos);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					emissions = rs.getInt("count");
				}
			} catch(SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					c.close();
					
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
		
		return emissions;
	}
	
	public int getTotalEmissionsForWord(String word) {
		int emissions = 0;
		
		Connection c = dbm.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		if(c != null) {
			try {
				pstmt = c.prepareStatement(GET_TOTAL_EMISSIONS);
				pstmt.setString(1, word);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					emissions = rs.getInt("sum");
				}
			} catch(SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					c.close();
					
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
		
		return emissions;
	}
}
