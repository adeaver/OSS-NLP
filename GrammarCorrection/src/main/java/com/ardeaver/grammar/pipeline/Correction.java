package com.ardeaver.grammar.pipeline;

public class Correction {
	private String sentence;
	private double probability;
	
	public Correction(String sentence, double probability) {
		this.sentence = sentence;
		this.probability = probability;
	}

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}
	
	public String toString() {
		return sentence;
	}
}
