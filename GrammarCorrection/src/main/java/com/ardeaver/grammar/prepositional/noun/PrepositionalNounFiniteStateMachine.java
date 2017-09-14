package com.ardeaver.grammar.prepositional.noun;

import com.ardeaver.grammar.prepositional.PrepositionalPhraseFiniteStateMachine;

/**
 * The implementation for Prepositional Phrases starting with nouns
 * 
 * @author Andrew Deaver
 * @version 1.0
 * @since 2017-02-08
 */
public class PrepositionalNounFiniteStateMachine extends PrepositionalPhraseFiniteStateMachine {

	public PrepositionalNounFiniteStateMachine() {
		super(new PrepositionalNounTransductionFactory());
	}

}
