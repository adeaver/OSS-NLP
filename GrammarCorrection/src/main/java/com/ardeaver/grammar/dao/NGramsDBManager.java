package com.ardeaver.grammar.dao;


/**
 * Inherited Implementation of the DB Manager for the spotcheck database
 * 
 * @author Andrew Deaver
 * @version 1.0
 * @since 2017-03-21
 */
public class NGramsDBManager extends DBManager {
	
	public NGramsDBManager() {
		super("ALEA_NGRAM_HOST", "ALEA_NGRAM_USER", "ALEA_NGRAM_PWD");
	}
}
