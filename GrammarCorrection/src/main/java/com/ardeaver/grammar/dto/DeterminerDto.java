package com.ardeaver.grammar.dto;

public class DeterminerDto {
	private int count;
	private String word;
	
	public DeterminerDto() {}
	
	public DeterminerDto(int count, String word) {
		this.count = count;
		this.word = word;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
}
