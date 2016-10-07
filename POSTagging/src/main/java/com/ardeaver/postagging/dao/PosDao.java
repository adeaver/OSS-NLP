package com.ardeaver.postagging.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PosDao {
	private static final String GET_ALL_POS = "SELECT * FROM pos_tags";
	
	private DatabaseManager dbm;
	
	public PosDao() {
		dbm = new DatabaseManager();
	}
	
	public List<String> getPartsOfSpeech() {
		List<String> posTags = new ArrayList<String>();
		
		Connection c = dbm.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		if(c != null) {
			try {
				pstmt = c.prepareStatement(GET_ALL_POS);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					posTags.add(rs.getString("pos"));
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
		
		return posTags;
	}
}
