package com.ardeaver.transition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ardeaver.fsa.util.TypeConverter;

public class EnglishNumberTransitionTable {
	private static final int[][][] transitionTable = {
		{{1,14}, {2,14}, {3,14}, null, null},
		{null, null, null, {6,14}, {5,14}},
		{null, null, null, null, {5,14}},
		{{4,14}, null, null, null, null},
		{null, null, null, null, {5,14}},
		{{10,14}, {14}, {11,14}, null, null},
		{{8,14}, {9,14}, {7,14}, null, {5,14}},
		{{8}, null, null, null, {5,14}},
		{null, null, null, null, {5,14}},
		{null, null, null, null, {5,14}},
		{null, null, null, {12,14}, null},
		{{14}, null, null, null, null},
		{{14}, {14}, {13,14}, null, null},
		{{14}, null, null, null, null},
		{null, null, null, null, null}
	};
	
	public static Optional<List<Integer>> getPossibleStates(int currentState, int inputGroup) {
		if(inputGroup != TypeConverter.F) {
			List<Integer> nodes = new ArrayList<Integer>();
			int[] nodeChecks = transitionTable[currentState][inputGroup];
			
			if(nodeChecks != null) {
				for(int node : nodeChecks) {
					nodes.add(new Integer(node));
				}
			}
			
			if(!nodes.isEmpty()) {
				return Optional.of(nodes);
			}
		}
		
		return Optional.empty();
		
	}
}
