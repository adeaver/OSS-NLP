package com.ardeaver.grammar.nounofnoun;

import java.util.Arrays;
import java.util.List;

import com.ardeaver.grammar.fsa.TransitionTable;

public class NounOfNounTransitionTable extends TransitionTable {
	
	private static final int[][][] TRANSITION_TABLE = {
		{{1}, null, null},
		{null, {2}, null},
		{{3}, null, null},
		{null, null, null}
	};

	public NounOfNounTransitionTable() {
		super(TRANSITION_TABLE);
	}

	@Override
	public List<Integer> getAcceptingStates() {
		return Arrays.asList(3);
	}

}
