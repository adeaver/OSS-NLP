package com.ardeaver.deconjugator.util;

public class Transition {
	private int nextState;
	private String[] output;
	
	public Transition(int stateTransition, String[] o) {
		nextState = stateTransition;
		output = o;
	}

	public int getNextState() {
		return nextState;
	}

	public void setPossibleStates(int nextState) {
		this.nextState = nextState;
	}

	public String[] getOutput() {
		return output;
	}

	public void setOutput(String[] output) {
		this.output = output;
	}
}
