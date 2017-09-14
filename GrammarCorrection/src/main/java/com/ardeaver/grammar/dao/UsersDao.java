package com.ardeaver.grammar.dao;

import java.util.List;

import com.ardeaver.grammar.web.models.User;

public class UsersDao extends SQLClient {
	
	private static final String GET_USER_BY_NAME = "SELECT username, id, email, lang FROM users WHERE username=?";
	private static final String STORE_USER = "INSERT INTO users(username, password, email, lang) VALUES(?, ?, ?, ?)";
	private static final String GET_USER_BY_PASSWORD_AND_NAME = "SELECT username, id, email, lang FROM users WHERE username=? AND password=?";
	
	public UsersDao() {
		super(new UserManager());
	}

	public User getUserByUsername(String username) {
		String[] params = {username};
		return executeUserQuery(GET_USER_BY_NAME, params);
	}
	
	public User getUserByUsernameAndPassword(String username, String password) {
		String[] params = {username, password};
		return executeUserQuery(GET_USER_BY_PASSWORD_AND_NAME, params);
	}
	
	public void insertUser(String username, String password, String email, String lang) {
		String[] params = {username, password, email, lang};
		
		super.executeInsertQuery(STORE_USER, params);
	}
	
	private User executeUserQuery(String query, String[] params) {
		String[] columns = {"username", "id", "email", "lang"};
		
		List<User> users = super.executeQuery(query, params, columns, columns, User.class);
		
		return users.size() > 0 ? users.get(0) : null;
	}
}
