package com.ardeaver.transition;

import com.ardeaver.fsa.util.DateInputConverter;

public class DateTransitionTable {
	private static final Integer[][] stateMap = {
		{11, 1, 2, 3, null, null, null, 4, null},
		{null, null, null, null, 11, null, null, null, null},
		{null, null, null, null, null, 11, null, null, null},
		{null, null, null, null, null, null, 11, null, null},
		{null, null, null, null, 5, 6, 7, null, null},
		{null, null, null, null, null, null, null, null, 8},
		{null, null, null, null, null, null, null, null, 9},
		{null, null, null, null, null, null, null, null, 10},
		{null, 11, null, null, null, null, null, null, null},
		{null, 11, 11, null, null, null, null, null, null},
		{null, 11, 11, 11, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null}};
	
	public static Integer getNextState(int currentState, int inputGroup) {
		if(inputGroup != DateInputConverter.J.intValue()) {
			return stateMap[currentState][inputGroup];
		}
		
		return null;
	}
}
