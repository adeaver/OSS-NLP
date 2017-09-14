package com.ardeaver.grammar.dto;

public class IndefiniteDto {
	private String word, indefinite;
	
	public IndefiniteDto() {}
	
	public IndefiniteDto(String word, String indefinite) {
		this.word = word;
		this.indefinite = indefinite;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getIndefinite() {
		return indefinite;
	}

	public void setIndefinite(String indefinite) {
		this.indefinite = indefinite;
	}
}
