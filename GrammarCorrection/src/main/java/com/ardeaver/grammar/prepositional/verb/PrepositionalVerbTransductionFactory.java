package com.ardeaver.grammar.prepositional.verb;

import com.ardeaver.grammar.fsa.Transduction;
import com.ardeaver.grammar.fsa.TransductionFactory;

/**
 * TransductionFactory for Prepositional Verb submodule
 * 
 * @author Andrew Deaver
 * @version 1.0
 * @since 2017-02-08
 */
public class PrepositionalVerbTransductionFactory extends TransductionFactory {

	@Override
	public Transduction getTransduction() {
		// TODO Auto-generated method stub
		return new PrepositionalVerbTransduction();
	}

}
