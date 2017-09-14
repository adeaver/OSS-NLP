package com.ardeaver.grammar.dto;

import com.ardeaver.grammar.ngrams.Bigram;

public class CountBigram extends Bigram {
	private int count;
	
	public CountBigram() {}
	
	public CountBigram(String word1, String word2, int count) {
		super(word1, word2);
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
