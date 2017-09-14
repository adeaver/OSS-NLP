package com.ardeaver.grammar.phrasal;

import java.util.Arrays;
import java.util.List;

import com.ardeaver.grammar.fsa.TransitionTable;

public class PhrasalVerbTransitionTable extends TransitionTable {
	
	private static final int[][][] TABLE = {
		{{1}, null, null, null, null},
		{null, {2}, {5}, {3}, null},
		{null, {4}, {6}, {7}, null},
		{null, {7}, null, null, null},
		{null, null, null, null, null},
		{null, {4}, null, null, null},
		{null, null, null, null, null},
		{null, {4}, null, null, null}
	};

	public PhrasalVerbTransitionTable() {
		super(TABLE);
	}

	@Override
	public List<Integer> getAcceptingStates() {
		return Arrays.asList(4, 5, 6, 7);
	}

}
