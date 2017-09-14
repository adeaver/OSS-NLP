package com.ardeaver.grammar.determiner;

import java.util.Arrays;
import java.util.List;

import com.ardeaver.grammar.fsa.TransitionTable;

public class DeterminerTransitionTable extends TransitionTable {
	
	private static final int[][][] TABLE = {
		{{2}, {4, 5}, {7}, {3}, {1}},
		{{2}, {4, 5}, {7}, {3}, null},
		{null, {4, 5}, {7}, {3}, null},
		{null, {4, 5}, {7}, null, null},
		{null, {6}, null, null, null},
		{null, null, null, null, null},
		{null, null, null, null, null},
		{null, null, null, null, null}
	};

	public DeterminerTransitionTable() {
		super(TABLE);
	}

	@Override
	public List<Integer> getAcceptingStates() {
		return Arrays.asList(5, 6, 7);
	}
	
}
