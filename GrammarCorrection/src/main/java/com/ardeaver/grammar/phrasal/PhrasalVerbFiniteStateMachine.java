package com.ardeaver.grammar.phrasal;

import com.ardeaver.grammar.fsa.FiniteStateMachine;

public class PhrasalVerbFiniteStateMachine extends FiniteStateMachine {

	public PhrasalVerbFiniteStateMachine() {
		super(new PhrasalVerbTransitionTable(), new PhrasalInputConverter(), new PhrasalVerbTransductionFactory(), false);
	}

}
