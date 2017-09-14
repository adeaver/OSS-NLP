package com.ardeaver.grammar.dao;

/**
 * The database manager for the part of speech database
 * 
 * @author Andrew Deaver
 * @version 1.0
 * @since 2017-03-21
 */
public class PartOfSpeechManager extends DBManager {

	public PartOfSpeechManager() {
		super("ALEA_POS_HOST", "ALEA_POS_USER", "ALEA_POS_PWD");
	}

}
