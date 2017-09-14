package com.ardeaver.grammar.nounofnoun;

import java.util.ArrayList;
import java.util.List;

import com.ardeaver.grammar.fsa.Transduction;
import com.ardeaver.grammar.preprocessing.Token;

public class NounOfNounTransduction implements Transduction {
	private ArrayList<Token> nouns;
	private int beginIndex, endIndex;
	private NounOfNounInputConverter converter;
	
	public NounOfNounTransduction() {
		nouns = new ArrayList<Token>();
		converter = new NounOfNounInputConverter();
		beginIndex = 0;
		endIndex = 0;
	}

	public void addToOutput(Token token, int currentState, int currentIndex) {
		int inputType = converter.convertInputToType(token);
		
		if(inputType == NounOfNounInputConverter.NOUN) {
			nouns.add(token);
			
			if(nouns.size() == 1) {
				beginIndex = currentIndex;
			} else {
				endIndex = currentIndex;
			}
		}
	}
	
	public List<Token> getNouns() {
		return nouns;
	}

	public String getOutput() {
		String output = "";
		
		if(nouns.size() == 2) {
			output = nouns.get(0).getHeadWord() + " of " + nouns.get(1).getHeadWord();
		}
		
		return output;
	}

	public void copyState(Transduction transduction) {
		if(transduction instanceof NounOfNounTransduction) {
			nouns = ((NounOfNounTransduction) transduction).nouns;
			beginIndex = ((NounOfNounTransduction) transduction).beginIndex;
			endIndex = ((NounOfNounTransduction) transduction).endIndex;
		}
	}

	public int getBeginIndex() {
		return beginIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

}
