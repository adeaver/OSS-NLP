package com.ardeaver.grammar.prepositional;

import java.util.ArrayList;
import java.util.List;

import com.ardeaver.grammar.fsa.Transduction;
import com.ardeaver.grammar.preprocessing.Token;

/**
 * The Transduction implementation for PrepositionalPhrases
 * 
 * @author Andrew Deaver
 * @version 1.0
 * @since 2017-02-08
 */
public abstract class PrepositionalPhraseTransduction implements Transduction {
	
	// ***** INSTANCE VARIABLES ******
	
	// The verbs/nouns that make up the prepositional phrases
	private List<Token> mains, extras;
	
	// The beginning and ending indices of the transduction
	private int beginIndex, endIndex;
	
	// Whether or not this transduction is parseable by the submodule
	// Gets set by the addToOutput function
	private boolean parseable;
	
	// ***** END INSTANCE VARIABLES *****
	
	
	/**
	 * Constructor for the transduction 
	 */
	public PrepositionalPhraseTransduction() {
		mains = new ArrayList<Token>();
		extras = new ArrayList<Token>();
		beginIndex = 0;
		endIndex = 0;
		parseable = false;
	}
	
	/**
	 * Adds a noun/verb token to the mains array list
	 * 
	 * @param token The noun/verb
	 */
	public void addToMains(Token token) {
		mains.add(token);
	}
	
	/**
	 * The getter for the mains arrayList
	 * 
	 * @return The list of all nouns and verbs that make up the prepositional phrase
	 */
	public List<Token> getMains() {
		return mains;
	}
	
	/**
	 * Adds a token to the extras
	 * 
	 * @param token The token to add
	 */
	public void addToExtras(Token token) {
		extras.add(token);
	}
	
	/**
	 * Gets the extra tokens
	 * 
	 * @return The extras tokens
	 */
	public List<Token> getExtras() {
		return extras;
	}

	public String getOutput() {
		// TODO Auto-generated method stub
		return "";
	}
	
	/**
	 * Setter for the parseable variable
	 * 
	 * @param parseable Whether or not the submodule can process this type of transduction
	 */
	public void setParseable(boolean parseable) {
		this.parseable = parseable;
	}
	
	/**
	 * The getter for the parseable variable
	 * 
	 * @return Whether or not the submodule can process this type of transduction
	 */
	public boolean isParseable() {
		return parseable;
	}

	public void copyState(Transduction transduction) {
		// TODO Auto-generated method stub
		if(transduction instanceof PrepositionalPhraseTransduction) {
			mains = ((PrepositionalPhraseTransduction) transduction).mains;
			extras = ((PrepositionalPhraseTransduction) transduction).extras;
			beginIndex = ((PrepositionalPhraseTransduction) transduction).beginIndex;
			endIndex = ((PrepositionalPhraseTransduction) transduction).endIndex;
			parseable = ((PrepositionalPhraseTransduction) transduction).parseable;
		}
	}

	public int getBeginIndex() {
		// TODO Auto-generated method stub
		return beginIndex;
	}
	
	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}

	public int getEndIndex() {
		// TODO Auto-generated method stub
		return endIndex;
	}
	
	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	public abstract void addToOutput(Token token, int currentState, int currentIndex);

}
