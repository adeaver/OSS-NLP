package com.ardeaver.grammar.prepositional.noun;

import com.ardeaver.grammar.prepositional.PrepositionalPhraseInputConverter;
import com.ardeaver.grammar.prepositional.PrepositionalPhraseTransduction;
import com.ardeaver.grammar.preprocessing.Token;

/**
 * Transductions for the noun submodule
 * 
 * @author Andrew Deaver
 * @version 1.0
 * @since 2017-02-08
 */
public class PrepositionalNounTransduction extends PrepositionalPhraseTransduction {
	
	// ***** INSTANCE VARIABLES *****
	private PrepositionalPhraseInputConverter converter;
	// ***** END INSTANCE VARIABLES *****
	
	/**
	 * Constructor
	 */
	public PrepositionalNounTransduction() {
		converter = new PrepositionalPhraseInputConverter();
	}

	@Override
	public void addToOutput(Token token, int currentState, int currentIndex) {
		int inputType = converter.convertInputToType(token);
		
		if(currentState == 0) {
			if(inputType == PrepositionalPhraseInputConverter.NOUN) {
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
