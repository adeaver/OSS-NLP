package com.ardeaver.deconjugator.util;

public class Path {
	private int currentState, currentIndex;
	private String currentString;
	
	public Path(int state, int index, String string) {
		currentState = state;
		currentIndex = index;
		currentString = string;
	}

	public int getCurrentState() {
		return currentState;
	}

	public void setCurrentState(int currentState) {
		this.currentState = currentState;
	}

	public int getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}

	public String getCurrentString() {
		return currentString;
	}

	public void setCurrentString(String currentString) {
		this.currentString = currentString;
	}
}
