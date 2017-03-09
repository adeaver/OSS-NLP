package com.ardeaver.earley.service;

import java.util.ArrayList;
import java.util.List;

import com.ardeaver.earley.dao.PartOfSpeechDao;
import com.ardeaver.earley.entity.Entity;
import com.ardeaver.earley.entity.PairEntity;
import com.ardeaver.earley.entity.SingleEntity;

public class Scanner {
	private PartOfSpeechDao partOfSpeechDao;
	private List<String> terminals;
	
	public Scanner() {
		partOfSpeechDao = new PartOfSpeechDao();
		terminals = partOfSpeechDao.getPartsOfSpeech();
	}
	
	public PairEntity scan(PairEntity start, Entity partOfSpeech, String word) {
		List<String> possiblePartsOfSpeech = partOfSpeechDao.getPartOfSpeechForWord(word);
		
		if(possiblePartsOfSpeech.contains(partOfSpeech.getHead())) {
			PairEntity copy = start.clone();
			
			List<Entity> newChildren = new ArrayList<Entity>();
			newChildren.add(new SingleEntity(word));
			
			copy.advanceStar(new PairEntity(new SingleEntity(partOfSpeech.getHead()), newChildren, 0, start.getStartIndex()+start.getStar(), start.getEndIndex()+1));
			return copy;
		}
		
		return null;
	}
	
	public boolean isTerminal(Entity partOfSpeech) {
		return terminals.contains(partOfSpeech.getHead());
	}
}
