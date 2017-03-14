package com.ardeaver.earley.probabilistic.entity;

import com.ardeaver.earley.entity.SingleEntity;

public class ProbabilisitcSingleEntity extends SingleEntity implements ProbabilisticEntity {
	
	public ProbabilisitcSingleEntity(String entity) {
		super(entity);
	}

	public double getProbability() {
		return 1;
	}
	
}
