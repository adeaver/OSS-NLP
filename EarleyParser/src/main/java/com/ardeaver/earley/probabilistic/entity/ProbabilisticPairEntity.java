package com.ardeaver.earley.probabilistic.entity;

import java.util.List;

import com.ardeaver.earley.entity.Entity;
import com.ardeaver.earley.entity.PairEntity;
import com.ardeaver.earley.entity.SingleEntity;

public class ProbabilisticPairEntity extends PairEntity implements ProbabilisticEntity, Comparable<ProbabilisticPairEntity> {

	private double probability;
	
	public ProbabilisticPairEntity(SingleEntity head, List<Entity> children,
			int star, int startIndex, int endIndex, double probability) {
		super(head, children, star, startIndex, endIndex);
		this.probability = probability;
	}
	
	public ProbabilisticPairEntity(PairEntity pairEntity, double probability) {
		super(pairEntity);
		this.probability = probability;
	}

	public double getProbability() {
		return probability;
	}

	@Override
	public void advanceStar(Entity newEntity) {
		ProbabilisticEntity pe;
		
		if(newEntity instanceof ProbabilisticEntity) {
			pe = (ProbabilisticEntity) newEntity;
			super.advanceStar(pe);
			this.probability += pe.getProbability();
		}
	}
	
	@Override
	public PairEntity clone() {
		return new ProbabilisticPairEntity(super.clone(), this.probability);
	}
	
	@Override
	public String toString() {
		return super.toString() + " [" + this.probability + "]";
	}

	public int compareTo(ProbabilisticPairEntity arg0) {
		int diff = (int) (arg0.getProbability()*100 - this.getProbability()*100);
		
		if(diff == 0) {
			return 1;
		}
		
		return diff;
	}
}
