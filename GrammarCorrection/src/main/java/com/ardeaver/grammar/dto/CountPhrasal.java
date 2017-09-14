package com.ardeaver.grammar.dto;

public abstract class CountPhrasal {
	private String verb;
	private String prep;
	private int count;
	
	public CountPhrasal() {}
	
	public CountPhrasal(String verb, String prep, int count) {
		this.verb = verb;
		this.prep = prep;
		this.count = count;
	}

	public String getVerb() {
		return verb;
	}

	public void setVerb(String verb) {
		this.verb = verb;
	}

	public String getPrep() {
		return prep;
	}

	public void setPrep(String prep) {
		this.prep = prep;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
