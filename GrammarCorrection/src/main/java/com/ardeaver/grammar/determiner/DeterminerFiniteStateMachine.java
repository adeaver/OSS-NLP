package com.ardeaver.grammar.determiner;

import com.ardeaver.grammar.fsa.FiniteStateMachine;

public class DeterminerFiniteStateMachine extends FiniteStateMachine{
	public DeterminerFiniteStateMachine() {
		super(new DeterminerTransitionTable(), new DeterminerInputConverter(), new DeterminerTransductionFactory());
	}
}
