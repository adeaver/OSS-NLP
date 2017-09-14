package com.ardeaver.grammar.determiner;

import com.ardeaver.grammar.fsa.InputConverter;
import com.ardeaver.grammar.preprocessing.Token;

public class DeterminerInputConverter implements InputConverter {
	
	public static final Integer DETERMINER = 0;
	public static final Integer NOUN = 1;
	public static final Integer PRONOUN = 2;
	public static final Integer ADJECTIVE = 3;
	public static final Integer OTHER = 4;
	
	public static final String[] VOWELS = {"a", "e", "i", "o", "u"};

	public int convertInputToType(Token token) {
		if(token.getPartOfSpeech().equals("DT")
				|| token.getPartOfSpeech().equals("Z")
				|| token.getPartOfSpeech().equals("PRP$")) {
			return DETERMINER;
		}
		
		if(token.getPartOfSpeech().startsWith("NN")) {
			return NOUN;
		}
		
		if(token.getPartOfSpeech().equals("PRP")) {
			return PRONOUN;
		}
		
		if(token.getPartOfSpeech().startsWith("JJ") 
				|| token.getPartOfSpeech().startsWith("RB")) {
			return ADJECTIVE;
		}
		
		return OTHER;
	}

}
