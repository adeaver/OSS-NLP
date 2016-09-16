package com.ardeaver.deconjugator.transitiontable;

import java.util.ArrayList;
import java.util.List;

import com.ardeaver.deconjugator.converter.RegularVerbInputConverter;
import com.ardeaver.deconjugator.util.Transition;


public class RegularVerbTransitionTable {
	private static final Integer[][][] stateTransition = {
		{{0}, {0, 1}, {0,3}, {0,2}, {0, 4}, {0}, {0}, {0}, null},
		{null, null, null, null, null, {6}, {7}, {6}, {5}},
		{null, null, null, null, null, {9}, {10}, {9}, {8}},
		{null, null, null, null, null, null, null, null, {11}},
		{null, null, null, null, null, null, {12}, null, null},
		{null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, {13}},
		{null, null, {14}, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null},
		{null, null, {17}, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, {18}, null},
		{null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, {19}, null},
		{null, null, null, null, null, null, null, {20}, null},
		{null, null, null, null, null, null, null, null, {21}},
		{null, null, null, null, null, null, null, null, {22}},
		{null, null, null, null, null, null, null, null, {23}},
		{null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null}};
	
	private static final String[][][][] outputTransition = {
		{{{"@"}}, {{"@"}, {"*"}}, {{"@"}, {"*"}}, {{"@"}, {"*"}}, {{"@"}, {"*"}}, {{"@"}}, {{"@"}}, {{"@"}}, null},
		{null, null, null, null, null, {{"*"}}, {{"*"}}, {{"*"}}, {{"-AR"}}},
		{null, null, null, null, null, {{"*"}}, {{"*"}}, {{"*"}}, {{"-ER", "-IR"}}},
		{null, null, null, null, null, null, null, null, {{"-AR", "-ER", "-IR"}}},
		{null, null, null, null, null, null, {{"*"}}, null, null},
		{null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, {{"-AR"}}},
		{null, null, {{"*"}}, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, {{"-ER", "-IR"}}},
		{null, null, {{"*"}}, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null},
		{null, null, {{"*"}}, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, {{"*"}}, null},
		{null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, {{"*"}}, null},
		{null, null, null, null, null, null, null, {{"*"}}, null},
		{null, null, null, null, null, null, null, null, {{"-AR"}}},
		{null, null, null, null, null, null, null, null, {{"-ER"}}},
		{null, null, null, null, null, null, null, null, {{"-IR"}}},
		{null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null}};
	
	public static List<Transition> getTransitionForInput(String token, int state) {
		Integer tokenIndex = RegularVerbInputConverter.processToken(token);
		Integer[] nextStates = stateTransition[state][tokenIndex];
		
		if(nextStates != null) {
			ArrayList<Transition> transitions = new ArrayList<Transition>();
			String[][] outputs = outputTransition[state][tokenIndex];
			
			for(int i = 0; i < nextStates.length; i++) {
				transitions.add(new Transition(nextStates[i], outputs[i]));
			}
			
			return transitions;
		}
		
		return null;
	}
		
}
