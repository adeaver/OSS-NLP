package com.ardeaver.grammar.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

/**
 * An abstract class for setting up connections to the database
 * 
 * @author Andrew Deaver
 * @version 1.0
 * @since 2017-03-21
 * 
 */
public abstract class DBManager {
	// ***** INSTANCE VARIABLES *****
	protected String host, user, pwd;
	// ***** END INSTANCE VARIABLES *****
	
	/**
	 * Constructor
	 * 
	 * @param host The database host
	 * @param user The database user
	 * @param pwd The database password
	 */
	public DBManager(String host, String user, String pwd) {
		Map<String, String> env = System.getenv();
		
		if(env.containsKey(host)) {
			this.host = env.get(host);
		} else {
			System.out.println("INFO: COULD NOT FIND HOST");
			this.host = host;
		}
		
		if(env.containsKey(user)) {
			this.user = env.get(user);
		} else {
			System.out.println("INFO: COULD NOT FIND USER");
			this.user = user;
		}
		
		if(env.containsKey(pwd)) {
			this.pwd = env.get(pwd);
		} else {
			System.out.println("INFO: COULD NOT FIND PWD");
			this.pwd = pwd;
		}
	}
	
	/**
	 * Returns a connection to the specified database
	 * 
	 * @return Connection object
	 */
	public Connection getConnection() {
		Connection conn = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(host, user, pwd);
		} catch(SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conn;
	}
}
