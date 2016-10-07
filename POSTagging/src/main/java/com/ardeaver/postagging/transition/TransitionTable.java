package com.ardeaver.postagging.transition;

import java.util.HashMap;
import java.util.List;

import com.ardeaver.postagging.dao.TransitionDao;
import com.ardeaver.postagging.dto.TransitionDto;

public class TransitionTable {
	private HashMap<String, Transition> table;
	
	public TransitionTable() {
		table = new HashMap<String, Transition>();
		this.initialize();
	}
	
	public double getTransitionProability(String fromState, String toState) {
		Transition t = table.get(fromState);
		
		if(t != null) {
			return t.getTransitionProbability(toState);
		}
		
		return 0;
	}
	
	private void initialize() {
		TransitionDao dao = new TransitionDao();
		List<TransitionDto> dtos = dao.getTransitions();
		
		for(TransitionDto dto : dtos) {
			Transition transition = table.getOrDefault(dto.getFromGram(), new Transition());
			transition.addNewState(dto.getToGram(), dto.getCount());
			table.put(dto.getFromGram(), transition);
		}
	}
}
