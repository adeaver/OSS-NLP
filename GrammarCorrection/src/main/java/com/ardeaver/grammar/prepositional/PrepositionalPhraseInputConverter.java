package com.ardeaver.grammar.prepositional;

import com.ardeaver.grammar.fsa.InputConverter;
import com.ardeaver.grammar.preprocessing.Token;

/**
 * This is the input converter used by all prepositional phrases
 * It has four classes, viewed below.
 * 
 * @author Andrew Deaver
 * @version 1.0
 * @since 2017-02-08
 */
public class PrepositionalPhraseInputConverter implements InputConverter {
	
	// ***** CLASS VARIABLES *****
	
	public static final Integer NOUN = 0;
	public static final Integer VERB = 1;
	public static final Integer PREP = 2;
    public static final Integer CONT = 3;
	public static final Integer OTHER = 4;
	
	// ***** END CLASS VARIABLES *****

	public int convertInputToType(Token token) {		
		if(token.getPartOfSpeech().contains("NN") || 
				token.getPartOfSpeech().contains("PPS")) {
			return NOUN;
		}
		
		if(token.getPartOfSpeech().contains("VB") || token.getPartOfSpeech().contains("MD") || 
				token.getPartOfSpeech().contains("BE") || token.getPartOfSpeech().contains("DO") ||
				token.getPartOfSpeech().contains("HV")) {
			return VERB;
		}
		
		if(token.getPartOfSpeech().contains("TO") 
					|| token.getPartOfSpeech().contains("RP") || token.getPartOfSpeech().contains("IN")) {
			return PREP;
		}
		
        if(token.getPartOfSpeech().contains("JJ") || token.getPartOfSpeech().contains("DT") ||
            token.getPartOfSpeech().contains("RB") || token.getPartOfSpeech().contains("Z") || 
            token.getPartOfSpeech().equals("PRP$")) {
            return CONT;
        }

		return OTHER;
	}

}
