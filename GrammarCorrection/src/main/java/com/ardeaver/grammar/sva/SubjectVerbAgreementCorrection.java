package com.ardeaver.grammar.sva;

/**
 * This is a DTO class for corrections once they have come out of the DAO
 * class for the SVA module
 * 
 * @author Andrew Deaver
 * @version 1.0
 * @since 2017-02-28
 */
public class SubjectVerbAgreementCorrection {
	
	// ***** INSTANCE VARIABLES *****
	
	private String word;
	private int index;
	
	// ***** END INSTANCE VARIABLES *****
	
	/**
	 * Constructor
	 * 
	 * @param word The new word
	 * @param index The index in the correction (this is different than the index in the input string)
	 */
	public SubjectVerbAgreementCorrection(String word, int index) {
		this.word = word;
		this.index = index;
	}

	/**
	 * The getter for the word
	 * 
	 * @return The replacement word
	 */
	public String getWord() {
		return word;
	}

	/**
	 * The setter for the word
	 * 
	 * @param word The new word to set
	 */
	public void setWord(String word) {
		this.word = word;
	}

	/**
	 * The getter for the index
	 * 
	 * @return The index inside of the correction
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * The setter for the index
	 * 
	 * @param index The new index inside of the correction
	 */
	public void setIndex(int index) {
		this.index = index;
	}
}
