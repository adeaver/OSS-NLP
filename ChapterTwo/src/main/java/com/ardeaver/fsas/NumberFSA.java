package com.ardeaver.fsas;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Optional;

import com.ardeaver.fsa.util.Path;
import com.ardeaver.fsa.util.TypeConverter;
import com.ardeaver.transition.EnglishNumberTransitionTable;

public class NumberFSA {
	public boolean isValid(String input) {
		Deque<Path> operationArchive = new ArrayDeque<Path>();
		Optional<List<Integer>> possiblePaths;
		
		List<String> keys = processInput(input);
		int currentIndex = 0;
		int currentState = 0;
		
		while(currentIndex < keys.size()) {
			Integer type = TypeConverter.findGroup(keys.get(currentIndex));
			possiblePaths = EnglishNumberTransitionTable.getPossibleStates(currentState, type);
			
			if(possiblePaths.isPresent()) {
				for(Integer p : possiblePaths.get()) {
					if(p != null) {
						operationArchive.push(new Path(currentIndex+1, p));
					}
				}
			} else {
				if(operationArchive.isEmpty()) {
					currentState = 0;
					break;
				}
			}
			
			Path nPath = operationArchive.pop();
			currentIndex = nPath.getIndex();
			currentState = nPath.getState();
		}
		
		return (currentState == 14);
	}
	
	private List<String> processInput(String input) {
		String processedInput = input.trim().replaceAll("-", " ").replaceAll(" and ", " ");
		String[] inputUnits = processedInput.split(" ");
		return Arrays.asList(inputUnits);
	}
}
