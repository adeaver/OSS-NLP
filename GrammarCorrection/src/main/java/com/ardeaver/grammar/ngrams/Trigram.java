package com.ardeaver.grammar.ngrams;

public class Trigram extends Bigram {
	private String middleWord;
	
	public Trigram() {};
	
	public Trigram(String firstWord, String middleWord, String lastWord) {
		super(firstWord, lastWord);
		this.middleWord = middleWord;
	}
	
	public Trigram(Trigram trigram) {
		super(trigram);
		this.middleWord = trigram.middleWord;
	}
	
	public String getMiddleWord() {
		return middleWord;
	}

	public void setMiddleWord(String middleWord) {
		this.middleWord = middleWord;
	}

	@Override
	public String toString() {
		return super.toString() + " " + middleWord;
	}
}
