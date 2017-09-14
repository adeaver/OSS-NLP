package com.ardeaver.grammar.fsa;

import com.ardeaver.grammar.preprocessing.Token;

/**
 * The InputConverter class translates an input token
 * to a specific group of input. For instance, a certain transition may
 * be governed by a group of part of speech objects (i.e. nouns or conjunctions)
 * and that would group them together.
 * 
 * @author Andrew Deaver
 * @version 1.0
 * @since 2017-02-06
 */
public interface InputConverter {
	/**
	 * This method, to be implemented, takes in a token
	 * and returns what type of input it is.
	 * 
	 * @param token The token to check
	 * @return What input group it is in
	 */
	public int convertInputToType(Token token);
}
