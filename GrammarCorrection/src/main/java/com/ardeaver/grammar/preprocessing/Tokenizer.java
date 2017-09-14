package com.ardeaver.grammar.preprocessing;

import java.util.ArrayList;
import java.util.List;

/**
 * The Tokenizer class transforms sentences into lists of tokens and
 * list of tokens into sentences.
 * 
 * @author Andrew Deaver
 * @version 1.0
 * @since 2017-02-06
 */
public class Tokenizer {
	/**
	 * Transforms a word into a token
	 * 
	 * @param word The word to transform
	 * @return A token representation of the word
	 */
	public static Token tokenizeWord(String word) {
		return new Token(word);
	}
	
	/**
	 * A sentence to tokenize
	 * 
	 * @param input The sentence as a string
	 * @return A list of tokens representing the sentence
	 */
	public static List<Token> tokenizeInput(String input) {
		List<Token> tokens = new ArrayList<Token>();
		String[] parts = cleanInput(input).split("\\s+");
		
		for(String p : parts) {
			tokens.add(new Token(p));
		}
		
		return tokens;
	}
	
	/**
	 * A string representation of the list of tokens
	 * 
	 * @param tokens A list of tokens
	 * @return A string representation
	 */
	public static String getSentence(List<Token> tokens) {
		String output = "";
		
		for(Token t : tokens) {
			output += t.toString() + " ";
		}
		
		return output.trim();	
	}
	
	/**
	 * Converts the list of tokens to a tagged string
	 * 
	 * @param tokens The list of tokens
	 * @return A tagged string of the tokens
	 */
	public static String getTaggedSentence(List<Token> tokens) {
		String output = "";
		
		for(Token t : tokens) {
			output += t.getTaggedToken() + " ";
		}
		
		return output.trim();
	}
	
	/**
	 * This method removes unhelpful characters
	 * 
	 * @param input The input string
	 * @return A cleaned representation of the input
	 */
	private static String cleanInput(String input) {
		String regex = "[\\.!,;:?\\)\\(]";
		
		return input.replaceAll(regex, "");
	}
}
