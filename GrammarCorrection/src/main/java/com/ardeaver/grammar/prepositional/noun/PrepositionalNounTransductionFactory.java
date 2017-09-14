package com.ardeaver.grammar.prepositional.noun;

import com.ardeaver.grammar.fsa.Transduction;
import com.ardeaver.grammar.fsa.TransductionFactory;

/**
 * TransductionFactory for Prepositional Noun submodule
 * 
 * @author Andrew Deaver
 * @version 1.0
 * @since 2017-02-08
 */
public class PrepositionalNounTransductionFactory extends TransductionFactory {

	@Override
	public Transduction getTransduction() {
		// TODO Auto-generated method stub
		return new PrepositionalNounTransduction();
	}

}
