package com.ardeaver.grammar.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class SQLClient {
	private DBManager db;
	
	public SQLClient(DBManager db) {
		this.db = db;
	}
	
	protected void executeInsertQuery(String query, String[] params) {
		Connection c = db.getConnection();
		PreparedStatement pstmt = null;
		
		if(c != null) {
			try {
				c.setAutoCommit(false);
				pstmt = c.prepareStatement(query);
				
				for(int i = 0; i < params.length; i++) {
					pstmt.setString(i+1, params[i]);
				}
				
				pstmt.executeUpdate();
				c.commit();
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
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	protected int executeCountQuery(String query, String[] params, String[] columns) {
		ArrayList<ArrayList<Object>> results = getResults(query, params, columns);
		
		if(results.size() > 0) {
			if(results.get(0).size() > 0 && results.get(0).get(0) != null) {
				return Integer.parseInt(results.get(0).get(0).toString());
			}
		}
		
		return -1;
	}
	
	protected double executeDoubleQuery(String query, String[] params, String[] columns) {
		ArrayList<ArrayList<Object>> results = getResults(query, params, columns);
		
		if(results.size() > 0) {
			if(results.get(0).size() > 0 && results.get(0).get(0) != null) {
				return Double.parseDouble(results.get(0).get(0).toString());
			}
		}
		
		return -1;
	}
	
	protected boolean executeBooleanQuery(String query, String[] params, String[] columns) {
		ArrayList<ArrayList<Object>> results = getResults(query, params, columns);
		
		if(results.size() > 0) {
			if(results.get(0).size() > 0 && results.get(0).get(0) != null) {
				return Long.valueOf(results.get(0).get(0).toString()).longValue() >= 1L ? Boolean.TRUE : Boolean.FALSE;
			}
		}
		
		return false;
	}
	
	protected <T> List<T> executeQuery(String query, String[] params, String[] columns, String[] columnMappings, Class<T> classReference) {
		ArrayList<ArrayList<Object>> results = getResults(query, params, columns);
		
		return zip(results, columnMappings, classReference);
	}
	
	protected List<String> executeListStringQuery(String query, String[] params, String[] columns) {
		ArrayList<ArrayList<Object>> results = getResults(query, params, columns);
		
		return unlist(results);
	}
	
	private <T> List<T> zip(ArrayList<ArrayList<Object>> results, String[] columns, Class<T> classReference) {
		String zip = "";
		List<T> list = new ArrayList<T>();
		
		ObjectMapper mapper = new ObjectMapper();
		
		for(int j = 0; j < results.size(); j++) {
			zip = "{";
			for(int i = 0; i < columns.length; i++) {
				zip += "\"" + StringEscapeUtils.escapeJava(columns[i]) + "\":";
				zip += stringizeParameter(results.get(j).get(i));
				zip += i < columns.length - 1 ? "," : "";
			}
			
			zip += "}";
			
			try {
				list.add(mapper.readValue(zip, classReference));
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	private String stringizeParameter(Object param) {
		if(param instanceof String) {
			return "\"" + StringEscapeUtils.escapeJava(param.toString()) + "\"";
		} else {
			return StringEscapeUtils.escapeJava(param.toString());
		}
	}
	
	private List<String> unlist(ArrayList<ArrayList<Object>> results) {
		ArrayList<String> unlist = new ArrayList<String>();
		
		for(ArrayList<Object> r : results) {
			if(r.size() > 0) {
				unlist.add(r.get(0).toString());
			}
		}
		
		return unlist;
	}
	
	private ArrayList<ArrayList<Object>> getResults(String query, String[] params, String[] columns) {
		Connection c = db.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList<ArrayList<Object>> results = new ArrayList<ArrayList<Object>>();
		int resultIndex = 0;
		
		if(c != null) {
			try {
				pstmt = c.prepareStatement(query);
				
				for(int i = 0; i < params.length; i++) {
					pstmt.setString(i+1, params[i]);
				}
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					results.add(new ArrayList<Object>());
					for(String column : columns) {
						results.get(resultIndex).add(rs.getObject(column));
					}
					resultIndex++;
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
