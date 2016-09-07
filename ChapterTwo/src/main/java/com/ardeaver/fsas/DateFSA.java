package com.ardeaver.fsas;

import com.ardeaver.fsa.util.DateInputConverter;
import com.ardeaver.transition.DateTransitionTable;


public class DateFSA {
	public boolean isValid(String input) {
		String[] keys = input.split(" ");
		int currentIndex = 0;
		Integer currentState = 0;
		Integer type = 0;
		
		while(currentIndex < keys.length) {
			if(currentState >= 1 && currentState <= 4) {
				try {
					Integer date = Integer.parseInt(keys[currentIndex]);
					type = DateInputConverter.getConvertedNumInputForState(currentState, date);
				} catch(NumberFormatException e) {
					currentState = 0;
					break;
				}
			} else {
				type = DateInputConverter.getConverteredInput(keys[currentIndex]);
			}
			
			currentState = DateTransitionTable.getNextState(currentState, type);
			currentIndex++;
			
			if(currentState == null) {
				currentState = 0;
				break;
			}
		}
		
		return currentState == 11;
	}
}
