package com.ardeaver.earley.probabilistic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ardeaver.earley.dao.PartOfSpeechDao;
import com.ardeaver.earley.entity.Entity;
import com.ardeaver.earley.entity.SingleEntity;
import com.ardeaver.earley.probabilistic.entity.ProbabilisticPairEntity;

public class ProbabilisiticScanner {

	private PartOfSpeechDao partOfSpeechDao;
	private List<String> terminals;
	
	public ProbabilisiticScanner() {
		partOfSpeechDao = new PartOfSpeechDao();
		terminals = partOfSpeechDao.getPartsOfSpeech();
	}
	
	
	public ProbabilisticPairEntity scan(ProbabilisticPairEntity start, Entity partOfSpeech, String word) {
		Map<String, Double> probability = partOfSpeechDao.getPartOfSpeechProbabilityForWord(word);
		
		if(probability.containsKey(partOfSpeech.getHead())) {
			ProbabilisticPairEntity copy = (ProbabilisticPairEntity) start.clone();
			
			List<Entity> newChildren = new ArrayList<Entity>();
			newChildren.add(new SingleEntity(word));
			
			copy.advanceStar(new ProbabilisticPairEntity(new SingleEntity(partOfSpeech.getHead()), newChildren, 0, start.getStartIndex()+start.getStar(), start.getEndIndex()+1, probability.get(partOfSpeech.getHead())));
			return copy;
		}
		
		return null;
	}
	
	public boolean isTerminal(Entity partOfSpeech) {
		return terminals.contains(partOfSpeech.getHead());
	}
}
