package com.ardeaver.grammar.prepositional.verb;

import com.ardeaver.grammar.prepositional.PrepositionalPhraseFiniteStateMachine;

/**
 * Finite State Machine for Prepositional Verb Submodule
 * 
 * @author Andrew Deaver
 * @version 1.0
 * @since 2017-02-08
 */
public class PrepositionalVerbFiniteStateMachine extends PrepositionalPhraseFiniteStateMachine {

	/**
	 * Constructor
	 */
	public PrepositionalVerbFiniteStateMachine() {
		super(new PrepositionalVerbTransductionFactory());
	}

}
