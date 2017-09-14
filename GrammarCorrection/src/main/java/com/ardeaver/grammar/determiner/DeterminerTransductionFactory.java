package com.ardeaver.grammar.determiner;

import com.ardeaver.grammar.fsa.Transduction;
import com.ardeaver.grammar.fsa.TransductionFactory;

public class DeterminerTransductionFactory extends TransductionFactory {

	@Override
	public Transduction getTransduction() {
		return new DeterminerTransduction();
	}

}
