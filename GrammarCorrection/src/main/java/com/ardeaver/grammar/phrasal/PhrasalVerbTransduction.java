package com.ardeaver.grammar.phrasal;

import java.util.ArrayList;
import java.util.List;

import com.ardeaver.grammar.fsa.Transduction;
import com.ardeaver.grammar.preprocessing.Token;

public class PhrasalVerbTransduction implements Transduction {
	private Token mainVerb;
	private List<Token> nouns, preps;
	private PhrasalInputConverter inputConverter;
	private boolean isPreposition;
	private int beginIndex, endIndex;
	
	public static final Integer PREPOSITION_TWO_NOUNS = 0;
	public static final Integer PREPOSITION_ONE_NOUN = 1;
	public static final Integer PARTICLE_ONE_NOUN = 2;
	public static final Integer PARTICLE_NO_NOUNS = 3;

	public PhrasalVerbTransduction() {
		mainVerb = null;
		nouns = new ArrayList<Token>();
		preps = new ArrayList<Token>();
		inputConverter = new PhrasalInputConverter();
		isPreposition = false;
		this.beginIndex = 0;
		this.endIndex = 0;
	}
	
	public Token getMainVerb() {
		return mainVerb;
	}

	public List<Token> getNouns() {
		return nouns;
	}

	public List<Token> getPreps() {
		return preps;
	}

	public void addToOutput(Token token, int currentState, int currentIndex) {
		int inputType = inputConverter.convertInputToType(token);
		
		if(inputType == PhrasalInputConverter.VERB && currentState == 0) {
			mainVerb = token;
			this.beginIndex = currentIndex;
		}
		
		if(inputType == PhrasalInputConverter.NOUN) {
			nouns.add(token);
			this.endIndex = currentIndex;
		}
		
		if(inputType == PhrasalInputConverter.PARTICLE || inputType == PhrasalInputConverter.PREP) {
			preps.add(token);
			this.endIndex = currentIndex;
			
			if(inputType == PhrasalInputConverter.PREP) {
				isPreposition = true;
			}
		}
	}

	public String getOutput() {
		String output = mainVerb + " NOUNS" + nouns + " PREPS" + preps;
		return output;
	}

	public void copyState(Transduction transduction) {
		if(transduction instanceof PhrasalVerbTransduction) {
			mainVerb = ((PhrasalVerbTransduction) transduction).mainVerb;
			nouns.addAll(((PhrasalVerbTransduction) transduction).nouns);
			preps.addAll(((PhrasalVerbTransduction) transduction).preps);
			isPreposition = ((PhrasalVerbTransduction) transduction).isPreposition;
			beginIndex = ((PhrasalVerbTransduction) transduction).beginIndex;
			endIndex = ((PhrasalVerbTransduction) transduction).endIndex;
		}
	}
	
	public int getBeginIndex() {
		return beginIndex;
	}
	
	public int getEndIndex() {
		return endIndex;
	}
	
	public int getType() {
		if(isPreposition) {
			if(nouns.size() == 2) {
				return PREPOSITION_TWO_NOUNS;
			}
			
			return PREPOSITION_ONE_NOUN;
		}
		
		if(nouns.size() == 1) {
			return PARTICLE_ONE_NOUN;
		}
		
		return PARTICLE_NO_NOUNS;
	}

}
