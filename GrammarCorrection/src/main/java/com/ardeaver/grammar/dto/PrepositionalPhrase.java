package com.ardeaver.grammar.dto;

public class PrepositionalPhrase {
	private String word1, word2, prep, lemma1, lemma2;
	
	public PrepositionalPhrase() {};
	
	public PrepositionalPhrase(String word1, String word2, String prep, String lemma1, String lemma2) {
		this.word1 = word1;
		this.word2 = word2;
		this.prep = prep;
		this.lemma1 = lemma1;
		this.lemma2 = lemma2;
	}

	public String getWord1() {
		return word1;
	}

	public void setWord1(String word1) {
		this.word1 = word1;
	}

	public String getWord2() {
		return word2;
	}

	public void setWord2(String word2) {
		this.word2 = word2;
	}

	public String getPrep() {
		return prep;
	}

	public void setPrep(String prep) {
		this.prep = prep;
	}

	public String getLemma1() {
		return lemma1;
	}

	public void setLemma1(String lemma1) {
		this.lemma1 = lemma1;
	}

	public String getLemma2() {
		return lemma2;
	}

	public void setLemma2(String lemma2) {
		this.lemma2 = lemma2;
	}
}
