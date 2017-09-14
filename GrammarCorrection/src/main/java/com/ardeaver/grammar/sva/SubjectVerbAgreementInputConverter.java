package com.ardeaver.grammar.sva;

import com.ardeaver.grammar.fsa.InputConverter;
import com.ardeaver.grammar.preprocessing.Token;

/**
 * This is the input converter for the SVA module
 * 
 * @author Andrew Deaver
 * @version 1.0
 * @since 2017-02-28
 */
public class SubjectVerbAgreementInputConverter implements InputConverter {
	
	// ***** CLASS VARIABLES *****
	public static final Integer NOUN = 0;
	public static final Integer CONNECTOR = 1;
	public static final Integer GERUND = 2;
	public static final Integer VERB = 3;
	public static final Integer AUX = 4;
	public static final Integer ADVERB = 5;
	public static final Integer CONJUNCTION = 6;
	public static final Integer POSSESSIVE = 7;
	public static final Integer OTHER = 8;
	// ***** END CLASS VARIABLES *****
	
	public int convertInputToType(Token token) {
		if(token.getPartOfSpeech().startsWith("NN") || token.getPartOfSpeech().startsWith("PPS") || 
				token.getPartOfSpeech().equals("PRP") | token.getPartOfSpeech().equals("EX")) {
			return NOUN;
		}
		if(token.getPartOfSpeech().startsWith("IN") ||
				token.getPartOfSpeech().startsWith("WPS") || token.getPartOfSpeech().startsWith("CS")) {
			return CONNECTOR;
		}
		if(token.getPartOfSpeech().equals("VBG")) {
			return GERUND;
		}
		if(token.getPartOfSpeech().startsWith("VB")) {
			return VERB;
		}
		if(token.getPartOfSpeech().startsWith("MD") || token.getPartOfSpeech().startsWith("BE") ||
				token.getPartOfSpeech().startsWith("DO") || token.getPartOfSpeech().startsWith("HV")) {
			return AUX;
		}
		if(token.getPartOfSpeech().startsWith("CC")) {
			return CONJUNCTION;
		}
		if(token.getPartOfSpeech().startsWith("RB") || token.getPartOfSpeech().startsWith("JJ")) {
			return ADVERB;
		}
		if(token.getPartOfSpeech().startsWith("POS")) {
			return POSSESSIVE;
		}
		return OTHER;
	}
}
