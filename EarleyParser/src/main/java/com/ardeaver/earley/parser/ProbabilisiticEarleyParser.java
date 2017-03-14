package com.ardeaver.earley.parser;

import java.util.ArrayList;
import java.util.List;

import com.ardeaver.earley.probabilistic.entity.ProbabilisticPairEntity;
import com.ardeaver.earley.probabilistic.service.ProbabilisiticCompleter;
import com.ardeaver.earley.probabilistic.service.ProbabilisiticPredictor;
import com.ardeaver.earley.probabilistic.service.ProbabilisiticScanner;

public class ProbabilisiticEarleyParser {
	
	private ProbabilisiticCompleter completer;
	private ProbabilisiticScanner scanner;
	private ProbabilisiticPredictor predictor;
	
	public ProbabilisiticEarleyParser() {
		completer = new ProbabilisiticCompleter();
		scanner = new ProbabilisiticScanner();
		predictor = new ProbabilisiticPredictor();
	}
	
	public List<ProbabilisticPairEntity> parseInput(String input) {
		List<String> tokens = tokenize(input);
		
		List<List<ProbabilisticPairEntity>> chart = new ArrayList<List<ProbabilisticPairEntity>>();
		chart.add(filterTooLong(predictor.getInitialList(), tokens.size()));
		
		ProbabilisticPairEntity entity, newEntity;
		
		for(int i = 0; i < tokens.size(); i++) {
			
			chart.add(new ArrayList<ProbabilisticPairEntity>());
			
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
		
		List<ProbabilisticPairEntity> finalPairs = new ArrayList<ProbabilisticPairEntity>();
		ProbabilisticPairEntity pairEntity;
		
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
	
	private List<ProbabilisticPairEntity> filterTooLong(List<ProbabilisticPairEntity> pairs, int maxTokens) {
		List<ProbabilisticPairEntity> filtered = new ArrayList<ProbabilisticPairEntity>();
		
		for(int i = 0; i < pairs.size(); i++) {
			if(pairs.get(i).getChildren().size() <= maxTokens) {
				filtered.add(pairs.get(i));
			}
		}
		
		return filtered;
	}
	
	void filterAdditions(List<ProbabilisticPairEntity> entities, List<ProbabilisticPairEntity> newEntities) {
		for(ProbabilisticPairEntity p : newEntities) {
			if(!entities.contains(p) && !p.getHead().equals("QUOTE")) {
				entities.add(p);
			}
		}
	}
	
	private List<ProbabilisticPairEntity> filterFinalAdditions(List<ProbabilisticPairEntity> toFinish) {
		List<ProbabilisticPairEntity> finished = new ArrayList<ProbabilisticPairEntity>();
		
		for(ProbabilisticPairEntity pair : toFinish) {
			if(pair.isComplete()) {
				finished.add(pair);
			}
		}
		
		return finished;
	}
}