package com.ardeaver.grammar.prepositional.verb;

import com.ardeaver.grammar.dao.PrepositionalVerbsDao;
import com.ardeaver.grammar.prepositional.PrepositionalPhraseService;

/**
 * CorrectionService for Prepositional Verb submodule
 * 
 * @author Andrew Deaver
 * @version 1.0
 * @since 2017-02-08
 */
public class PrepositionalVerbService extends PrepositionalPhraseService {

	public PrepositionalVerbService() {
		super(new PrepositionalVerbFiniteStateMachine(), new PrepositionalVerbsDao());
	}
	
}
