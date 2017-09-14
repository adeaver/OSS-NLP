package com.ardeaver.grammar.nounofnoun;

import com.ardeaver.grammar.fsa.Transduction;
import com.ardeaver.grammar.fsa.TransductionFactory;

public class NounOfNounTransductionFactory extends TransductionFactory {

	@Override
	public Transduction getTransduction() {
		return new NounOfNounTransduction();
	}

}
