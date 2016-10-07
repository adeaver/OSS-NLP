package com.ardeaver.postagging.transition;

import java.util.HashMap;

public class Transition {
	private int count;
	private HashMap<String, Integer> row;
	
	public Transition() {
		count = 0;
		row = new HashMap<String, Integer>();
	}
	
	public void addNewState(String toGram, int occurences) {
		row.put(toGram, occurences);
		count += occurences;
	}
	
	public double getTransitionProbability(String toState) {
		Integer toOccurences = row.getOrDefault(toState, 0);
		return toOccurences.intValue() / (double) count;
	}
}
