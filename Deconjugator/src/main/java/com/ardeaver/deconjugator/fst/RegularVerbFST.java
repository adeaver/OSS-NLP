package com.ardeaver.deconjugator.fst;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import com.ardeaver.deconjugator.transitiontable.RegularVerbTransitionTable;
import com.ardeaver.deconjugator.util.DeconstructedVerb;
import com.ardeaver.deconjugator.util.Path;
import com.ardeaver.deconjugator.util.Transition;

public class RegularVerbFST {
	public static DeconstructedVerb unconjugate(String verb) {
		verb += "#";
		String[] tokens = verb.split("");
		ArrayDeque<Path> toVisit = new ArrayDeque<Path>();
		
		int currentState = 0;
		int currentIndex = 0;
		String currentString = "";
		
		while(currentIndex < tokens.length) {
			List<Transition> transitions = RegularVerbTransitionTable.getTransitionForInput(tokens[currentIndex], currentState);
			if(transitions != null) {
				for(Transition transition : transitions) {
					String addition = processOutput(tokens[currentIndex], transition.getOutput());
					if(!addition.equals("END")) {
						Path p = new Path(transition.getNextState(), currentIndex+1, currentString + addition);
						toVisit.push(p);
					} else {
						if(currentIndex == tokens.length - 1) {
							return new DeconstructedVerb(currentString, transition.getOutput());
						}
					}
				}
			}
			
			if(!toVisit.isEmpty()) {
				Path newPath = toVisit.pop();
				currentState = newPath.getCurrentState();
				currentIndex = newPath.getCurrentIndex();
				currentString = newPath.getCurrentString();
			} else {
				break;
			}
			
		}
		
		return null;
		
	}
	
	private static String processOutput(String token, String[] transition) {
		if(transition[0].equals("@")) {
			return token;
		} else if(transition[0].equals("*")) {
			return "";
		}
		
		return "END";
	}
}
