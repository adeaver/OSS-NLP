package com.ardeaver.grammar.determiner;

import com.ardeaver.grammar.fsa.Transduction;
import com.ardeaver.grammar.preprocessing.Token;

public class DeterminerTransduction implements Transduction {
	private Token start, determiner, noun;
	private int beginIndex, endIndex;
	private DeterminerInputConverter converter;
	
	public DeterminerTransduction() {
		start = null;
		determiner = null;
		noun = null;
		beginIndex = 0;
		endIndex = 0;
		converter = new DeterminerInputConverter();
	}

	public void addToOutput(Token token, int currentState, int currentIndex) {
		int type = converter.convertInputToType(token);
		
		if(currentState == 0) {
			if(type == DeterminerInputConverter.OTHER) {
				start = token;
			}
			
			beginIndex = currentIndex;
		}
		
		if(currentState <= 1) {
			if(type == DeterminerInputConverter.DETERMINER) {
				determiner = token;
			}
		}
		
		if(currentState <= 5) {
			if(type == DeterminerInputConverter.NOUN) {
				noun = token;
				endIndex = currentIndex;
			}
		}
		
		if(currentState < 4) {
			if(type == DeterminerInputConverter.PRONOUN) {
				noun = token;
				endIndex = currentIndex;
			}
		}
		
	}

	public String getOutput() {
		String output = "";
		
		if(determiner != null) {
			output += determiner.getHeadWord() + " ";
		}
		
		if(noun != null) {
			output += noun.getHeadWord();
		}
		
		return output;
	}

	public void copyState(Transduction transduction) {
		if(transduction instanceof DeterminerTransduction) {
			noun = ((DeterminerTransduction) transduction).noun;
			determiner = ((DeterminerTransduction) transduction).determiner;
			start = ((DeterminerTransduction) transduction).start;
			beginIndex = ((DeterminerTransduction) transduction).beginIndex;
			endIndex = ((DeterminerTransduction) transduction).endIndex;
		}
	}

	public int getBeginIndex() {
		return beginIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}
	
	public Token getStart() {
		return start;
	}
	
	public Token getNoun() {
		return noun;
	}
	
	public Token getDeterminer() {
		return determiner;
	}

}
