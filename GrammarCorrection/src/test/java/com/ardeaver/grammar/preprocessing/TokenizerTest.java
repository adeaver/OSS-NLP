package com.ardeaver.grammar.preprocessing;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class TokenizerTest {
	@Test
	public void testTokenization() {
		String input = "I want to go to the store.";
		List<Token> tokens = Tokenizer.tokenizeInput(input);
		
		String output = Tokenizer.getSentence(tokens);
		String tagged = Tokenizer.getTaggedSentence(tokens);
		
		assertEquals(7, tokens.size());
		assertEquals("I want to go to the store", output);
		assertEquals("(I)_NA (want)_NA (to)_NA (go)_NA (to)_NA (the)_NA (store)_NA", tagged);
	}
}
