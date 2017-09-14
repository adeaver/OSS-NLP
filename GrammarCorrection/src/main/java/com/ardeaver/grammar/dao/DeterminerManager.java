package com.ardeaver.grammar.dao;

/**
 * Inherited version of DBManager for Determiners
 * 
 * @author Andrew Deaver
 * @version 1.0
 * @since 2017-03-21
 */
public class DeterminerManager extends DBManager {

	public DeterminerManager() {
		super("ALEA_DET_HOST", "ALEA_DET_USER", "ALEA_DET_PWD");
	}
	
}
