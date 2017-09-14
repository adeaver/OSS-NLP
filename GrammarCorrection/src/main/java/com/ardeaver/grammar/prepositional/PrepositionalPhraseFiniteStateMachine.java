package com.ardeaver.grammar.prepositional;

import com.ardeaver.grammar.fsa.FiniteStateMachine;
import com.ardeaver.grammar.fsa.TransductionFactory;

/**
 * This is the general finite state machine for prepositional phrases. Both
 * prepositional phrase modules implement this, providing their own transduction factory
 * 
 * @author Andrew Deaver
 * @version 1.0
 * @since 2017-02-08
 */
public abstract class PrepositionalPhraseFiniteStateMachine extends FiniteStateMachine {
	// ***** CLASS VARAIBLES *****
	
	private static final PrepositionalPhraseTransitionTable TRANSITION_TABLE = new PrepositionalPhraseTransitionTable();
	private static final PrepositionalPhraseInputConverter INPUT_CONVERTER = new PrepositionalPhraseInputConverter();
	
	// ***** END CLASS VARIABLES

	/**
	 * Constructor for the PrepositionalPhraseFiniteStateMachine
	 * This creates a FiniteStateMachine after being provided a transductionFactory
	 * by all implementing classes.
	 * 
	 * @param transductionFactory
	 */
	public PrepositionalPhraseFiniteStateMachine(TransductionFactory transductionFactory) {
		super(TRANSITION_TABLE, INPUT_CONVERTER, transductionFactory);
	}

}
