package com.ardeaver.earley.service;

import java.util.ArrayList;
import java.util.List;

import com.ardeaver.earley.entity.PairEntity;

public class Completer {
	
	public List<PairEntity> complete(List<PairEntity> pairs, PairEntity completed) {
		List<PairEntity> newEntities = new ArrayList<PairEntity>();
		
		PairEntity copy;
		
		for(PairEntity p : pairs) {
			if(p.getNext().getHead().equals(completed.getHead())) {
				copy = p.clone();
				copy.advanceStar(completed);
				
				newEntities.add(copy);
			}
		}
		
		return newEntities;
	}
}
