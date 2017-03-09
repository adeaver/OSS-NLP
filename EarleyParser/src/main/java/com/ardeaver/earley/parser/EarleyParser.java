package com.ardeaver.earley.parser;

import java.util.ArrayList;
import java.util.List;

import com.ardeaver.earley.entity.Entity;
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
	
	public Entity parseSentence(String input) {
		List<String> tokens = tokenize(input);
		List<List<PairEntity>> chart = new ArrayList<List<PairEntity>>();
		
		PairEntity entity, newEntity;
		
		chart.add(predictor.getInitialList());
		
		for(int i = 0; i < tokens.size(); i++) {
			
			chart.add(new ArrayList<PairEntity>());
			
			for(int j = 0; j < chart.get(i).size(); j++) {
				
				entity = chart.get(i).get(j);
				
				if(entity.isComplete()) {
					chart.get(i).addAll(completer.complete(chart.get(entity.getStartIndex()), entity));
				} else {
					if(scanner.isTerminal(entity.getNext())) {
						newEntity = scanner.scan(entity, entity.getNext(), tokens.get(i));
						
						if(newEntity != null) {
							chart.get(i+1).add(newEntity);
						}
					} else {
						addNoDuplicates(chart.get(i), predictor.predict(entity));
					}
				}
			}
		}
		
		System.out.println(chart.get(tokens.size()));
		
		return null;
	}
	
	private List<String> tokenize(String input) {
		List<String> list = new ArrayList<String>();
		String[] tokens = input.split("\\s+");
		
		for(String t : tokens) {
			list.add(t);
		}
		
		return list;
	}
	
	private void addNoDuplicates(List<PairEntity> entities, List<PairEntity> newEntities) {
		for(PairEntity p : newEntities) {
			if(!entities.contains(p)) {
				entities.add(p);
			}
		}
	}
}
