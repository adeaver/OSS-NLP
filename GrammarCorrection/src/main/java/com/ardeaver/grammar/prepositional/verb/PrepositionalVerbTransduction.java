package com.ardeaver.grammar.prepositional.verb;

import com.ardeaver.grammar.prepositional.PrepositionalPhraseInputConverter;
import com.ardeaver.grammar.prepositional.PrepositionalPhraseTransduction;
import com.ardeaver.grammar.preprocessing.Token;

/**
 * Transduction for Prepositional verb submodule
 * 
 * @author Andrew Deaver
 * @version 1.0
 * @since 2017-02-08
 */
public class PrepositionalVerbTransduction extends PrepositionalPhraseTransduction {
	
	// ***** INSTANCE VARIABLES *****
	private PrepositionalPhraseInputConverter converter;
	// ***** END INSTANCE VARIABLES *****
	
	/**
	 * Constructor
	 */
	public PrepositionalVerbTransduction() {
		converter = new PrepositionalPhraseInputConverter();
	}

	@Override
	public void addToOutput(Token token, int currentState, int currentIndex) {
		int inputType = converter.convertInputToType(token);
		
		if(currentState == 0) {
			if(inputType == PrepositionalPhraseInputConverter.VERB && token.getLength() == 1) {
				setParseable(true);
				addToMains(token);
				setBeginIndex(currentIndex);
			}
		}
		
		if(currentState != 0) {
			if(currentState == 2 && inputType == PrepositionalPhraseInputConverter.CONT) {
				addToExtras(token);
			}
			
			if(inputType == PrepositionalPhraseInputConverter.NOUN ||
					inputType == PrepositionalPhraseInputConverter.VERB) {
				addToMains(token);
				
				setEndIndex(currentIndex);
			}	
		}
	}

}
