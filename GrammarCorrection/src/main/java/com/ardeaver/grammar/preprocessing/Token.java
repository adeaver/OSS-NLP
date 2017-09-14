package com.ardeaver.grammar.preprocessing;

/**
 * The Token class is the smallest operable unit. Sometimes it is one word
 * sometimes it can be multiple words that get operated on by a unit.
 * 
 * Example: Phrasal Verbs are a token, "Share the website with me" is a single token.
 * 
 * @author Andrew Deaver
 * @version 1.0
 * @since 2017-02-06
 */
public class Token {
	// ***** INSTANCE VARIABLES *****
	
	// The main word to operate on
	private String headWord;
	
	// The part of speech of the token
	private String partOfSpeech;
	
	// The entire token
	private String[] token;
	
	// The index of the head word
	private int headWordIndex;
	
	// ***** END INSTANCE VARIABLES *****
	
	/**
	 * @param headWord
	 * @param headWordIndex
	 * @param token
	 * @param partOfSpeech
	 */
	public Token(String headWord, int headWordIndex, String[] token, String partOfSpeech) {
		this.headWord = headWord;
		this.headWordIndex = headWordIndex;
		this.token = token;
		this.partOfSpeech = partOfSpeech;
	}
	
	/**
	 * The constructor for the token class
	 * 
	 * @param headWord The main word to act on
	 * @param headWordIndex The index of the head word
	 * @param token The entire token
	 */
	public Token(String headWord, int headWordIndex, String[] token) {
		this(headWord, headWordIndex, token, "NA");
	}
	
	/**
	 * The constructor for the token class with a single word
	 * 
	 * @param headWord The head word for the token
	 */
	public Token(String headWord) {
		this(headWord, 0, null);
		this.token = new String[1];
		this.token[0] = headWord;
	}

	/**
	 * The getter for the head word
	 * 
	 * @return The head word
	 */
	public String getHeadWord() {
		return headWord;
	}

	/**
	 * The setter for the head word
	 * 
	 * @param headWord The new head word
	 */
	public void setHeadWord(String headWord) {
		this.headWord = headWord;
		this.token[headWordIndex] = headWord;
	}

	/**
	 * The getter for the part of speech
	 * 
	 * @return The part of speech
	 */
	public String getPartOfSpeech() {
		return partOfSpeech;
	}

	/**
	 * The setter for the part of speech
	 * 
	 * @param partOfSpeech The new part of speech
	 */
	public void setPartOfSpeech(String partOfSpeech) {
		this.partOfSpeech = partOfSpeech;
	}

	/**
	 * The getter for the entire contents of the token
	 * 
	 * @return The token as a list of words
	 */
	public String[] getToken() {
		return token;
	}

	/**
	 * The setter for the token
	 * 
	 * @param token The new list of words that represent the token
	 */
	public void setToken(String[] token) {
		this.token = token;
	}

	/**
	 * The getter for the head word index
	 * 
	 * @return The index of the head word
	 */
	public int getHeadWordIndex() {
		return headWordIndex;
	}

	/**
	 * The setter for the index of the head word
	 * 
	 * @param headWordIndex The new index for the head word
	 */
	public void setHeadWordIndex(int headWordIndex) {
		this.headWordIndex = headWordIndex;
	}
	
	/**
	 * The getter for the length of this token
	 * 
	 * @return The getter for the length of this token
	 */
	public int getLength() {
		return token.length;
	}
	
	public Token clone() {
		String[] newTokens = token;
		return new Token(headWord, headWordIndex, newTokens, partOfSpeech);
	}
	
	@Override
	public String toString() {
		String output = "";
		
		for(String w : this.token) {
			output += w + " ";
		}
		
		return output.trim();
	}
	
	/**
	 * This method returns a string representation of the tagged token
	 * 
	 * @return a string representation of the tagged token
	 */
	public String getTaggedToken() {
		String output = "(";
		
		for(String w : this.token) {
			output += w + "/";
		}
		
		output = output.substring(0, output.length()-1) + ")_" + this.partOfSpeech;
		
		return output;
	}
}
