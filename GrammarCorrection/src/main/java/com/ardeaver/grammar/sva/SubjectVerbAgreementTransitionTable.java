package com.ardeaver.grammar.sva;

import java.util.Arrays;
import java.util.List;

import com.ardeaver.grammar.fsa.TransitionTable;

/**
 * The transition table for the SVA module. Also, possibly the most annoying class I've ever written.
 * 
 * @author Andrew Deaver
 * @version 1.0
 * @since 2017-02-28
 */
public class SubjectVerbAgreementTransitionTable extends TransitionTable {
	
	// ***** CLASS VARIABLES *****
	private static final int[][][] TRANSITION_TABLE = {
		{{1}, null, null, null, {2}, null, null, null, null},
		{{1}, {4}, {4}, {15, 16}, {15, 16}, {14}, {3}, {0}, null},
		{{5, 2}, {2}, {2}, {2}, {2}, {2}, {2}, {2}, {2}},
		{{1}, {3}, {3}, {3}, {3}, {3}, {3}, {3}, {3}},
		{{6, 1}, {4}, {4}, {4}, {4}, {4}, {4}, {4}, {4}},
		{null, null, {16}, {16}, {15, 16}, {14}, null, null, null},
		{null, null, {10}, {9, 10}, {9}, {8}, {7}, {4}, null},
		{{6}, {7}, {7}, {7}, {7}, {7}, {7}, {7}, {7}},
		{null, null, {10}, {10}, {9, 10}, {8}, null, null, null},
		{{1, 4}, {4}, {4, 10}, {4, 10, 20}, {4, 10, 12, 20}, {11}, null, null, {4}},
		{{1, 4}, {4}, {4}, {4, 20}, {4, 20}, {4}, null, null, {4}},
		{{1, 4}, {4}, {4, 10}, {4, 10, 20}, {4, 10, 12, 20}, {4, 11}, null, null, {4}},
		{{1, 4}, {4}, {4, 10}, {4, 10, 20}, {4, 10, 20}, {4, 13}, null, null, {4}},
		{{1, 4}, {4}, {4, 10}, {4, 10, 20}, {4, 10, 20}, {4, 13}, null, null, {4}},
		{null, null, {16}, {16}, {15, 16}, {14}, null, null, null},
		{null, null, {16}, {16}, {18, 16}, {17}, null, null, null},
		{null, null, null, null, null, null, null, null, null},
		{null, null, {16}, {16}, {18, 16}, {17}, null, null, null},
		{null, null, {16}, {16}, {16}, {19}, null, null, null},
		{null, null, {16}, {16}, {16}, {19}, null, null, null},
		{null, null, null, {16}, {15, 16}, {14}, null, null, null}
	};
	// ***** END CLASS VARIABLES *****

	/**
	 * Constructor
	 */
	public SubjectVerbAgreementTransitionTable() {
		super(TRANSITION_TABLE);
	}

	@Override
	public List<Integer> getAcceptingStates() {
		return Arrays.asList(16);
	}

}
