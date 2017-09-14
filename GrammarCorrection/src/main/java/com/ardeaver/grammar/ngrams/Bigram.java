package com.ardeaver.grammar.ngrams;

public class Bigram {
	private String firstWord, lastWord;
	
	public Bigram() {};
	
	public Bigram(String firstWord, String lastWord) {
		this.firstWord = firstWord;
		this.lastWord = lastWord;
	}
	
	public Bigram(Bigram bigram) {
		this.firstWord = bigram.firstWord;
		this.lastWord = bigram.lastWord;
	}

	public String getFirstWord() {
		return firstWord;
	}

	public void setFirstWord(String firstWord) {
		this.firstWord = firstWord;
	}

	public String getLastWord() {
		return lastWord;
	}

	public void setLastWord(String lastWord) {
		this.lastWord = lastWord;
	}
	
	@Override
	public String toString() {
		return firstWord + " " + lastWord;
	}
}
