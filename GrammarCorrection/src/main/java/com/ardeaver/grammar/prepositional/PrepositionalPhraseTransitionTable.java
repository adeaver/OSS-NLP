package com.ardeaver.grammar.prepositional;

import java.util.Arrays;
import java.util.List;

import com.ardeaver.grammar.fsa.TransitionTable;

/**
 * The transition table for Prepositional Phrases
 * 
 * @author Andrew Deaver
 * @version 1.0
 * @since 2017-02-08
 */
public class PrepositionalPhraseTransitionTable extends TransitionTable {
	
	/**
	 * The transition table as a multidimensional array 
	 */
	private static final int[][][] TRANSITION_TABLE = {
		{{1}, {1}, null, null, null},
		{{3}, {3}, {2}, null, null},
		{{3}, {3}, null, {2}, null},
		{null, null, null, null, null}
	};

	/**
	 * Constructor
	 */
	public PrepositionalPhraseTransitionTable() {
		super(TRANSITION_TABLE);
	}

	@Override
	public List<Integer> getAcceptingStates() {
		// TODO Auto-generated method stub
		return Arrays.asList(3);
	}
}
