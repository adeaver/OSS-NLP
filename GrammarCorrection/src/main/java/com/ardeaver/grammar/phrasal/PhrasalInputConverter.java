package com.ardeaver.grammar.phrasal;

import com.ardeaver.grammar.fsa.InputConverter;
import com.ardeaver.grammar.preprocessing.Token;

public class PhrasalInputConverter implements InputConverter {
	
	public static final Integer VERB = 0;
	public static final Integer NOUN = 1;
	public static final Integer PARTICLE = 2;
	public static final Integer PREP = 3;
	public static final Integer OTHER = 4;

	public int convertInputToType(Token token) {
		if(token.getPartOfSpeech().contains("VB") || token.getPartOfSpeech().contains("MD") 
				|| token.getPartOfSpeech().contains("BE") || token.getPartOfSpeech().contains("HB")) {
			return VERB;
		}
		
		if(token.getPartOfSpeech().contains("NN") || token.getPartOfSpeech().contains("PPS") 
				|| token.getPartOfSpeech().contains("PRP")) {
			return NOUN;
		}
		
		if(token.getPartOfSpeech().startsWith("RB") || token.getPartOfSpeech().contains("RP")) {
			return PARTICLE;
		}
		
		if(token.getPartOfSpeech().contains("TO") || token.getPartOfSpeech().contains("IN")) {
			return PREP;
		}
		
		return OTHER;
	}

}
