package com.ardeaver.grammar.phrasal;

import com.ardeaver.grammar.fsa.Transduction;
import com.ardeaver.grammar.fsa.TransductionFactory;

public class PhrasalVerbTransductionFactory extends TransductionFactory {

	@Override
	public Transduction getTransduction() {
		return new PhrasalVerbTransduction();
	}

}
