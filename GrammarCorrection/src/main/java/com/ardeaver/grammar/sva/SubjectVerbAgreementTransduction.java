package com.ardeaver.grammar.sva;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ardeaver.grammar.fsa.Transduction;
import com.ardeaver.grammar.preprocessing.Token;
import com.ardeaver.grammar.preprocessing.Tokenizer;

/**
 * This is the implementation of the transductions used by the SVA module
 * 
 * @author Andrew Deaver
 * @version 1.0
 * @since 2017-02-28
 */
public class SubjectVerbAgreementTransduction implements Transduction {
	
	// ***** CLASS VARIABLES *****
	private static final List<Integer> GERUND_STATES = Arrays.asList(5, 14, 15, 16, 17, 18, 19);
	private static final List<Integer> VERB_STATES = Arrays.asList(0, 1, 5, 6, 14, 15, 16, 17, 18, 19, 20);
	private static final List<Integer> NOUN_STATES = Arrays.asList(0, 1, 2, 3);
	private static final List<Integer> ADVERB_STATES = Arrays.asList(15, 16, 17, 18, 19);
	// ***** END CLASS VARIABLES *****
	
	// ***** INSTANCE VARIABLES *****
	private List<Token> headNoun;
	private List<Token> verbsAdverbs;
	private int beginIndex, endIndex;
	private SubjectVerbAgreementTransductionType type;
	
	private SubjectVerbAgreementInputConverter inputConverter;
	// ***** END INSTANCE VARIABLES *****
	
	/**
	 * Constructor
	 */
	public SubjectVerbAgreementTransduction() {
		headNoun = new ArrayList<Token>();
		verbsAdverbs = new ArrayList<Token>();
		beginIndex = 0;
		endIndex = 0;
		type = SubjectVerbAgreementTransductionType.NORMAL;
		
		inputConverter = new SubjectVerbAgreementInputConverter();
	}

	public void addToOutput(Token token, int currentState, int currentIndex) {
		int inputType = inputConverter.convertInputToType(token);
		
		if((inputType == SubjectVerbAgreementInputConverter.NOUN || 
				inputType == SubjectVerbAgreementInputConverter.CONJUNCTION) &&
				NOUN_STATES.contains(currentState)) {
			headNoun.add(token);
			
			if(verbsAdverbs.size() == 0) {
				beginIndex = currentIndex;
			}
		}
		
		if(currentState == 1 && inputType == SubjectVerbAgreementInputConverter.POSSESSIVE) {
			headNoun.clear();
		}
		
		if((inputType == SubjectVerbAgreementInputConverter.VERB || inputType == SubjectVerbAgreementInputConverter.AUX) &&
				VERB_STATES.contains(currentState)) {
			verbsAdverbs.add(token);
			
			if(headNoun.isEmpty()) {
				type = SubjectVerbAgreementTransductionType.AUXILIARY;
				beginIndex = currentIndex;
			} else {
				if(verbsAdverbs.size() == 1 || (verbsAdverbs.size() == 2 && type == SubjectVerbAgreementTransductionType.AUXILIARY)) {
					endIndex = currentIndex;
				}
			}
		}
		
		if(inputType == SubjectVerbAgreementInputConverter.ADVERB && 
				ADVERB_STATES.contains(currentState)) {
			verbsAdverbs.add(token);
			
			if(verbsAdverbs.size() == 2 && type == SubjectVerbAgreementTransductionType.AUXILIARY) {
				endIndex = currentIndex;
			}
		}
		
		if(inputType == SubjectVerbAgreementInputConverter.GERUND && 
				GERUND_STATES.contains(currentState)) {
			verbsAdverbs.add(token);
		}
	}

	public String getOutput() {
		return "Head Noun: " + headNoun + "\n" + "Adverbs/Verbs: " + verbsAdverbs;
	}

	public void copyState(Transduction transduction) {
		if(transduction instanceof SubjectVerbAgreementTransduction) {
			headNoun = ((SubjectVerbAgreementTransduction) transduction).headNoun;
			verbsAdverbs = ((SubjectVerbAgreementTransduction) transduction).verbsAdverbs;
			beginIndex = ((SubjectVerbAgreementTransduction) transduction).beginIndex;
			endIndex = ((SubjectVerbAgreementTransduction) transduction).endIndex;
			type = ((SubjectVerbAgreementTransduction) transduction).type;
		}
	}

	public int getBeginIndex() {
		return beginIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	/**
	 * The getter for the head noun of the transduction
	 * 
	 * @return The head noun, this will determine how verbs are conjugated (is that a word in English?)
	 */
	public Token getHeadNoun() {
		if(headNoun.size() > 1) {
			if(headNoun.get(1).getHeadWord().toLowerCase().equals("and")) {
				Token token = Tokenizer.tokenizeWord("they");
				token.setPartOfSpeech("NNS");
				return token;
			} else if(headNoun.get(1).getHeadWord().toLowerCase().equals("or")) {
				Token token = Tokenizer.tokenizeWord("he");
				token.setPartOfSpeech("NN");
				return token;
			} else {
				return headNoun.get(0);
			}
		} else if (headNoun.size() == 0) {
			return null;
		} else {
			return headNoun.get(0);
		}
	}

	/**
	 * The getter for the words to operate on
	 * 
	 * @return The words to operate on
	 */
	public List<Token> getVerbsAdverbs() {
		return verbsAdverbs;
	}

	/**
	 * The getter for the type of the Transduction, i.e. whether it's an auxiliary construction
	 * or a normal construction
	 * 
	 * @return The type of the transduction
	 */
	public SubjectVerbAgreementTransductionType getType() {
		return type;
	}
}
