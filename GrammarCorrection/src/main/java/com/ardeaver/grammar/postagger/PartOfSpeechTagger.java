package com.ardeaver.grammar.postagger;

import java.util.List;

import com.ardeaver.grammar.preprocessing.Token;

public class PartOfSpeechTagger {
	private MarkovImplementation markovImplementation;
	
	public PartOfSpeechTagger() {
		markovImplementation = new MarkovImplementation();
	}
	
	public List<Token> tagSentence(List<Token> tokens) {
		List<String> tags = markovImplementation.getPartsOfSpeech(tokens);
		
		for(int i = 0; i < tokens.size(); i++) {
			tokens.get(i).setPartOfSpeech(tags.get(i));
		}
		
		return tokens;
	}
}
