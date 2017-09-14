package com.ardeaver.grammar.sva;

import com.ardeaver.grammar.preprocessing.Token;

/**
 * This is a DTO class for combinations of words that affect each other. This could be
 * the head noun and a verb or two verbs.
 * 
 * @author Andrew Deaver
 * @version 1.0
 * @since 2017-02-28
 */
public class SubjectVerbAgreementPair {
	
	// ***** INSTANCE VARIABLES *****
	private Token token1, token2;
	private int index1, index2;
	// ***** END INSTANCE VARIABLES *****
	
	/**
	 * Constructor
	 * 
	 * @param token1 The first word
	 * @param index1 The index in the correction of the first word
	 * @param token2 The second word
	 * @param index2 The index in the correction of the second word
	 */
	public SubjectVerbAgreementPair(Token token1, int index1, Token token2, int index2) {
		this.token1 = token1;
		this.token2 = token2;
		
		this.index1 = index1;
		this.index2 = index2;
	}

	/**
	 * The getter for the first word
	 * 
	 * @return The first word
	 */
	public Token getToken1() {
		return token1;
	}

	/**
	 * The setter for the first word
	 * 
	 * @param token1 The new first word
	 */
	public void setToken1(Token token1) {
		this.token1 = token1;
	}

	/**
	 * The getter for the second word
	 * 
	 * @return The second word in the pair
	 */
	public Token getToken2() {
		return token2;
	}

	/**
	 * The setter for the second word
	 * 
	 * @param token2 The new second word in the pair
	 */
	public void setToken2(Token token2) {
		this.token2 = token2;
	}

	/**
	 * The getter for the first index
	 * 
	 * @return The index of the first word
	 */
	public int getIndex1() {
		return index1;
	}

	/**
	 * The setter for the index of the first word
	 * 
	 * @param index1 The new index for the first word
	 */
	public void setIndex1(int index1) {
		this.index1 = index1;
	}

	/**
	 * The getter for the index of the second word
	 * 
	 * @return The index of the second word
	 */
	public int getIndex2() {
		return index2;
	}

	/**
	 * The setter for the index of the second word
	 * 
	 * @param index2 The new second word in the correction
	 */
	public void setIndex2(int index2) {
		this.index2 = index2;
	}
}
