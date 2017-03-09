package com.ardeaver.earley.parser;

import java.util.ArrayList;
import java.util.List;

import com.ardeaver.earley.entity.PairEntity;
import com.ardeaver.earley.service.Completer;
import com.ardeaver.earley.service.Predictor;
import com.ardeaver.earley.service.Scanner;

public class EarleyParser {
	private Predictor predictor;
	private Scanner scanner;
	private Completer completer;
	
	public EarleyParser() {
		scanner = new Scanner();
		predictor = new Predictor();
		completer = new Completer();
	}
	
	public List<PairEntity> parseSentence(String input) {
		List<String> tokens = tokenize(input);
		List<List<PairEntity>> chart = new ArrayList<List<PairEntity>>();
		
		PairEntity entity, newEntity;
		
		chart.add(filterTooLong(predictor.getInitialList(), tokens.size()));
		
		for(int i = 0; i < tokens.size(); i++) {
			
			chart.add(new ArrayList<PairEntity>());
			
			for(int j = 0; j < chart.get(i).size(); j++) {
				
				entity = chart.get(i).get(j);
				
				if(entity.isComplete()) {
					filterAdditions(chart.get(i), completer.complete(chart.get(entity.getStartIndex()), entity));
				} else {
					if(scanner.isTerminal(entity.getNext())) {
						newEntity = scanner.scan(entity, entity.getNext(), tokens.get(i));
						
						if(newEntity != null) {
							chart.get(i+1).add(newEntity);
						}
					} else {
						filterAdditions(chart.get(i), filterTooLong(predictor.predict(entity), tokens.size()-i));
					}
				}
			}
		}
		
		List<PairEntity> finalPairs = new ArrayList<PairEntity>();
		PairEntity pairEntity;
		
		for(int j = 0; j < chart.get(tokens.size()).size(); j++) {
			pairEntity = chart.get(tokens.size()).get(j);
			
			finalPairs.addAll(completer.complete(chart.get(pairEntity.getStartIndex()), pairEntity));
		}
		
		return filterFinalAdditions(finalPairs);
	}
	
	private List<String> tokenize(String input) {
		List<String> list = new ArrayList<String>();
		String[] tokens = input.split("\\s+");
		
		for(String t : tokens) {
			list.add(t);
		}
		
		return list;
	}
	
	private List<PairEntity> filterTooLong(List<PairEntity> pairs, int maxTokens) {
		List<PairEntity> filtered = new ArrayList<PairEntity>();
		
		for(int i = 0; i < pairs.size(); i++) {
			if(pairs.get(i).getChildren().size() <= maxTokens) {
				filtered.add(pairs.get(i));
			}
		}
		
		return filtered;
	}
	
	void filterAdditions(List<PairEntity> entities, List<PairEntity> newEntities) {
		for(PairEntity p : newEntities) {
			if(!entities.contains(p) && !p.getHead().equals("QUOTE")) {
				entities.add(p);
			}
		}
	}
	
	private List<PairEntity> filterFinalAdditions(List<PairEntity> toFinish) {
		List<PairEntity> finished = new ArrayList<PairEntity>();
		
		for(PairEntity pair : toFinish) {
			if(pair.isComplete()) {
				finished.add(pair);
			}
		}
		
		return finished;
	}
}
