package com.ardeaver.grammar.dto;

import com.ardeaver.grammar.ngrams.Trigram;

public class CountTrigram extends Trigram {
	private int count;
	
	public CountTrigram() {}
	
	public CountTrigram(String word1, String word2, String word3, int count) {
		super(word1, word2, word3);
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
