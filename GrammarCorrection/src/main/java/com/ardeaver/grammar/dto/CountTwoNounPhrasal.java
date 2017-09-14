package com.ardeaver.grammar.dto;

public class CountTwoNounPhrasal extends CountPhrasal {
	private String noun1, noun2;
	
	public CountTwoNounPhrasal() {
		super();
	}
	
	public CountTwoNounPhrasal(String verb, String prep, String noun1, String noun2, int count) {
		super(verb, prep, count);
		this.noun1 = noun1;
		this.noun2 = noun2;
	}

	public String getNoun1() {
		return noun1;
	}

	public void setNoun1(String noun1) {
		this.noun1 = noun1;
	}

	public String getNoun2() {
		return noun2;
	}

	public void setNoun2(String noun2) {
		this.noun2 = noun2;
	}
}
