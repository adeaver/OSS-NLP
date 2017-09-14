package com.ardeaver.grammar.fsa;

import com.ardeaver.grammar.preprocessing.Token;

/**
 * Transductions represent potential errors and are the output of
 * finite state machines. They typically contain information about the
 * position at which the potential error starts, where it ends, as well as the contents of it
 * 
 * @author Andrew Deaver
 * @version 1.0
 * @since 2017-02-06
 */
public interface Transduction {
	/**
	 * Given the current state, this determines how to add a token to the
	 * transduction. Be careful about adding a token during an accepting state
	 * since usually "OTHER" words should not be added to the transduction
	 * 
	 * @param token The token to (possibly) add
	 * @param currentState The current state of the FST
	 * @param currentIndex The current index of the FST
	 */
	public void addToOutput(Token token, int currentState, int currentIndex);
	
	/**
	 * Return a string representation to the output of the transduction.
	 * This method is a good candidate for refactoring, since I don't think it's used
	 * 
	 * @return A string representation of the transduction
	 */
	public String getOutput();
	
	/**
	 * Copies one transduction onto the current one because Java.
	 * 
	 * @param transduction The transduction to be copied.
	 */
	public void copyState(Transduction transduction);
	
	/**
	 * The getter for the beginning index of the transduction
	 * 
	 * @return The start index for the transduction
	 */
	public int getBeginIndex();
	
	/**
	 * The getter for the ending index of the transduction
	 * 
	 * @return The ending index for the transduction
	 */
	public int getEndIndex();
}
