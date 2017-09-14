package com.ardeaver.grammar.nounofnoun;

import com.ardeaver.grammar.fsa.InputConverter;
import com.ardeaver.grammar.preprocessing.Token;

public class NounOfNounInputConverter implements InputConverter {
	public static final Integer NOUN = 0;
	public static final Integer OF = 1;
	public static final Integer OTHER = 2;
	
	public int convertInputToType(Token token) {
			if(token.getPartOfSpeech().startsWith("NN") || 
					token.getPartOfSpeech().startsWith("PPS")) {
				return NOUN;
			}
			
			if(token.getHeadWord().toLowerCase().trim().equals("of")) {
				return OF;
			}
		
		return OTHER;
	}
}
