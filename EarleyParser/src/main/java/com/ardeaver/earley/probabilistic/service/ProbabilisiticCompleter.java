package com.ardeaver.earley.probabilistic.service;

import java.util.ArrayList;
import java.util.List;

import com.ardeaver.earley.probabilistic.entity.ProbabilisticPairEntity;

public class ProbabilisiticCompleter {
	public List<ProbabilisticPairEntity> complete(List<ProbabilisticPairEntity> pairs, ProbabilisticPairEntity completed) {
		List<ProbabilisticPairEntity> newEntities = new ArrayList<ProbabilisticPairEntity>();
		
		ProbabilisticPairEntity copy;
		
		for(ProbabilisticPairEntity p : pairs) {
			if(p.getNext() != null && p.getNext().getHead().equals(completed.getHead())) {
				copy = (ProbabilisticPairEntity) p.clone();
				copy.advanceStar(completed);
				
				newEntities.add(copy);
			}
		}
		
		return newEntities;
	}
}
