package com.ardeaver.grammar.test.util;

import java.util.ArrayList;
import java.util.List;

import com.ardeaver.grammar.preprocessing.Token;
import com.ardeaver.grammar.preprocessing.Tokenizer;

public class TestTokenizer {
	public static List<Token> tokenizeInput(String input) {
		List<Token> tokens = new ArrayList<Token>();
		String[] parts = input.split("\\s+");
		String[] pos;
		
		for(String p : parts) {
			pos = p.split("_");
			tokens.add(new Token(pos[0]));
			tokens.get(tokens.size()-1).setPartOfSpeech(pos[1]);
		}
		
		return tokens;
	}
	
	public static List<String> getSentences(List<List<Token>> tokens) {
		List<String> sentences = new ArrayList<String>();
		String sent;
		
		for(List<Token> t : tokens) {
			sent = Tokenizer.getSentence(t);
			sentences.add(sent);
		}
		
		return sentences;
	}
	
	public static String getSentence(List<Token> tokens) {
		return Tokenizer.getSentence(tokens);
	}
}
