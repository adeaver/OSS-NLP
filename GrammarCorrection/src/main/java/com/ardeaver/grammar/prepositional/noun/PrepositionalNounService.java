package com.ardeaver.grammar.prepositional.noun;

import com.ardeaver.grammar.dao.PrepositionalNounsDao;
import com.ardeaver.grammar.prepositional.PrepositionalPhraseService;

/**
 * The implementation of the CorrectionServices for the noun submodule
 * 
 * @author Andrew Deaver
 * @version 1.0
 * @since 2017-02-08
 */
public class PrepositionalNounService extends PrepositionalPhraseService {

	public PrepositionalNounService() {
		super(new PrepositionalNounFiniteStateMachine(), new PrepositionalNounsDao());
	}
	
}
