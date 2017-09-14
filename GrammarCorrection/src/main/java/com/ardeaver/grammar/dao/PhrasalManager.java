package com.ardeaver.grammar.dao;

/**
 * Database manager for Phrasal verbs
 * 
 * @author Andrew Deaver
 * @version 1.0
 * @since 2017-03-21
 */
public class PhrasalManager extends DBManager {

	public PhrasalManager() {
		super("ALEA_PHRASAL_HOST", "ALEA_PHRASAL_USER", "ALEA_PHRASAL_PWD");
	}

}
