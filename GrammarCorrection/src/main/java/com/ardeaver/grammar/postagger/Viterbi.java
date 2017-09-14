package com.ardeaver.grammar.postagger;

public class Viterbi {
	private int backIndex;
	private double probability;
	
	public Viterbi() {
		backIndex = 0;
		probability = 0;
	}
	
	public void insertViterbi(int index, double prob) {
		if(prob > probability) {
			backIndex = index;
			probability = prob;
		}
	}
	
	public int getBackIndex() {
		return backIndex;
	}
	
	public double getProbability() {
		return probability;
	}
}
