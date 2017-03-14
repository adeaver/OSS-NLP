package com.ardeaver.earley.dto;

import java.util.List;

public class ProbabilisiticPrediction extends Prediction {
	
	private double probability;
	
	public ProbabilisiticPrediction(String root, List<String> children, double probability) {
		super(root, children, 0);
		this.probability = probability;
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}
}
