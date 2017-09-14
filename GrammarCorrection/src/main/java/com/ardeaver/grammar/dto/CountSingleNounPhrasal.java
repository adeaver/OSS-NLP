package com.ardeaver.grammar.dto;

public class CountSingleNounPhrasal extends CountPhrasal {
	private String noun;
	
	public CountSingleNounPhrasal() {}
	
	public CountSingleNounPhrasal(String verb, String prep, String noun, int count) {
		super(verb, prep, count);
		this.noun = noun;
	}

	public String getNoun() {
		return noun;
	}

	public void setNoun(String noun) {
		this.noun = noun;
	}
}
