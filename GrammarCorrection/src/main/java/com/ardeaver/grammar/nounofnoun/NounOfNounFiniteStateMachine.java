package com.ardeaver.grammar.nounofnoun;

import com.ardeaver.grammar.fsa.FiniteStateMachine;

public class NounOfNounFiniteStateMachine extends FiniteStateMachine {

	public NounOfNounFiniteStateMachine() {
		super(new NounOfNounTransitionTable(), new NounOfNounInputConverter(), new NounOfNounTransductionFactory());
	}

}
