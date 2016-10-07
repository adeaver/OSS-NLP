package com.ardeaver.postagging.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ardeaver.postagging.dto.TransitionDto;

public class TransitionDao {
	private static final String GET_ALL_TRANSITIONS = "SELECT * FROM transitions;";
	
	private DatabaseManager dbm;
	
	public TransitionDao() {
		dbm = new DatabaseManager();
	}
	
	public List<TransitionDto> getTransitions() {
		List<TransitionDto> dtos = new ArrayList<TransitionDto>();
		
		Connection c = dbm.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String from, to;
		int count;
		
		TransitionDto dto;
		
		if(c != null) {
			try {
				pstmt = c.prepareStatement(GET_ALL_TRANSITIONS);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					from = rs.getString("from_gram");
					to = rs.getString("to_gram");
					count = rs.getInt("count");
					
					dto = new TransitionDto(from, to, count);
					dtos.add(dto);
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
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return dtos;
	}
}
