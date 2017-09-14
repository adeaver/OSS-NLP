package com.ardeaver.grammar.correction;

import java.util.ArrayList;
import java.util.List;

import com.ardeaver.grammar.dao.WordFormDao;
import com.ardeaver.grammar.preprocessing.Token;
import com.ardeaver.grammar.preprocessing.Tokenizer;

/**
 * The Correction class is a representation of a grammatical correction
 * it holds all the information to transfer between a transduction and a token in the
 * newly corrected string.
 * 
 * @author Andrew Deaver
 * @version 1.0
 * @since 2017-02-06
 */
public class Correction {
	// ***** Instance variables *****
	
	// The list of tokens that make up the correction
	private List<Token> tokens; 
	
	// indexes for where the correction should be applied and which token
	// is the main token to act on
	private int beginIndex, endIndex, headWordIndex; 
	
	// a data access object to figure out the root form of words.
	private WordFormDao wordFormDao;
	
	// The type of correction, the default is REPLACEMENT
	private CorrectionType correctionType;
	
	// ***** END INSTANCE VARIABLES *****
	
	
	/**
	 * The private constructor for the correction class. This requires
	 * all of the inheriting classes of CorrectionService to implement
	 * the correction builder
	 * 
	 * @param tokens The tokens that make up this correction
	 * @param headWordIndex Which token is the main word in the correction
	 * @param beginIndex Where the correction begins
	 * @param endIndex Where the correction ends
	 * @param correctionType What type the correction is
	 */
	private Correction(List<Token> tokens, int headWordIndex, int beginIndex, int endIndex, CorrectionType correctionType) {
		this.tokens = new ArrayList<Token>(tokens);
		this.headWordIndex = headWordIndex;
		this.beginIndex = beginIndex;
		this.endIndex = endIndex;
		this.wordFormDao = new WordFormDao();
		this.correctionType = correctionType;
	}
	
	/**
	 * This method allows to put in the token in its current form
	 * by index
	 * 
	 * @param token The token to add
	 * @param index The index to put the token in
	 */
	public void putToken(Token token, int index) {
		String[] wordsCopy = token.getToken().clone();
		Token t = new Token(token.getHeadWord(), token.getHeadWordIndex(), wordsCopy, token.getPartOfSpeech());
		t.setHeadWord(this.tokens.get(index).getHeadWord());
		this.tokens.set(index, t);
	}
	
	/**
	 * This method allows the token to put into the correction in
	 * its current form without knowing the index (i.e. if the order possibly changed -- phrasal verbs)
	 * Try to use this with caution
	 * 
	 * @param token The token to add
	 */
	public void putToken(Token token) {
		int index = findMatchingToken(token);
		
		if(index != -1) {
			putToken(token, index);
		}
	}
	
	/**
	 * The getter for the beginning index of this correction
	 * 
	 * @return the index to start from
	 */
	public int getBeginIndex() {
		return beginIndex;
	}
	
	/**
	 * The getter for the ending index of this correction
	 * 
	 * @return the index to end at
	 */
	public int getEndIndex() {
		return endIndex;
	}
	
	/**
	 * Converts this correction to a token
	 * 
	 * @return The token form of the correction
	 */
	public Token getToken() {
		String[] temp;
		List<String> parts = new ArrayList<String>();
		String headWord = "";
		int tokenHeadWordIndex = -1;
		
		for(int i = 0; i < tokens.size(); i++) {
			if(i == headWordIndex) {
				tokenHeadWordIndex = parts.size() + tokens.get(i).getHeadWordIndex();
				headWord = tokens.get(i).getHeadWord();
			}
			parts.addAll(convertArrayToList(tokens.get(i).getToken()));
		}
		
		temp = new String[parts.size()];
		parts.toArray(temp);
		
		return new Token(headWord, tokenHeadWordIndex, temp);
	}
	
	/**
	 * The getter for the type of the correction
	 * 
	 * @return the type of this correction
	 */
	public CorrectionType getType() {
		return this.correctionType;
	}
	
	public Correction clone() {
		return new Correction(new ArrayList<Token>(tokens), headWordIndex, beginIndex, endIndex, correctionType);
	}

	/**
	 * Finds the matching token in this correction given the head word of each token
	 * This is used since the correction services will initially put the headword in.
	 * 
	 * Example: "Share the website with my friends" will initially be put in as
	 * "Share website with friends"
	 * 
	 * @param t The token to find the index of
	 * @return The index of the token that the input matches
	 */
	private int findMatchingToken(Token t) {
		String lemma = wordFormDao.getLemma(t.getHeadWord());
		for(int i = 0; i < tokens.size(); i++) {
			if(wordFormDao.getLemma(tokens.get(i).getHeadWord()).equals(lemma)) {
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * Converts an Array to a list
	 * 
	 * @param array The array to convert
	 * @return The list
	 */
	private List<String> convertArrayToList(String[] array) {
		List<String> list = new ArrayList<String>();
		
		for(String s : array) {
			list.add(s);
		}
		
		return list;
	}
	
	/**
	 * The CorrectionBuilder class is used to create corrections
	 * 
	 * @author Andrew Deaver
	 * @version 1.0
	 * @since 2017-02-06
	 */
	public static class CorrectionBuilder {
		// ***** INSTANCE VARIABLES *****
		
		// The tokens that make up the correction
		private List<Token> tokens;
		
		// The beginning and ending indices as well as the index of the
		// main word (the head word)
		private int headWordIndex, beginIndex, endIndex;
		
		// The type of the correction
		private CorrectionType correctionType;
		
		// ***** END INSTANCE VARIABLES *****
		
		/**
		 * The constructor for the CorrectionBuilder
		 * 
		 * @param headWordIndex The index of the main word to act on
		 * @param beginIndex The beginning index of the correction
		 * @param endIndex The ending index of the correction
		 */
		public CorrectionBuilder(int headWordIndex, int beginIndex, int endIndex) {
			this.headWordIndex = headWordIndex;
			this.beginIndex = beginIndex;
			this.endIndex = endIndex;
			tokens = new ArrayList<Token>();
			this.correctionType = CorrectionType.REPLACEMENT;
		}
		
		/**
		 * The setter for the type of the correction
		 * 
		 * @param correctionType The new correction type
		 */
		public void setCorrectionType(CorrectionType correctionType) {
			this.correctionType = correctionType;
		}
		
		/**
		 * Adds a word to the set of tokens that make up
		 * this correction
		 * 
		 * @param word The word to add
		 */
		public void addWord(String word) {
			Token t = Tokenizer.tokenizeWord(word);
			tokens.add(t.clone());
		}
		
		/**
		 * This method creates a Correction instance
		 * 
		 * @return The correction based on this builder
		 */
		public Correction build() {
			return new Correction(new ArrayList<Token>(tokens), headWordIndex, beginIndex, endIndex, correctionType);
		}
	}
}
