package com.ardeaver.fsa.util;

public class Path {
	private int index;
	private int state;
	
	public Path(int i, int s) {
		index = i;
		state = s;
	}
	
	public int getIndex() {
		return index;
	}
	
	public int getState() {
		return state;
	}
}
