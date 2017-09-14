package com.ardeaver.grammar.postagger;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.ardeaver.grammar.preprocessing.Token;
import com.ardeaver.grammar.preprocessing.Tokenizer;

public class PartOfSpeechTaggerTest {
	private PartOfSpeechTagger tagger;
	
	public PartOfSpeechTaggerTest() {
		tagger = new PartOfSpeechTagger();
	}
	
	@Test
	public void testPartOfSpeechTagging() {
		String input = "He is a good person.";
		List<Token> outputTokens = tagger.tagSentence(Tokenizer.tokenizeInput(input));
		
		String taggedSentence = Tokenizer.getTaggedSentence(outputTokens);
		
		assertEquals("(He)_PRP (is)_BEZ (a)_Z (good)_JJ (person)_NN", taggedSentence);
	}
	
	@Test
	public void testIncorrectGrammar() {
		String input = "She have patient when she teach me difficult word";
		List<Token> outputTokens = tagger.tagSentence(Tokenizer.tokenizeInput(input));
		
		String taggedSentence = Tokenizer.getTaggedSentence(outputTokens);
		
		assertEquals("(She)_PRP (have)_HVP (patient)_NN (when)_WRB (she)_PRP (teach)_VB (me)_PRP (difficult)_JJ (word)_NN", taggedSentence);
	}
	
	@Test
	public void testIncorrect2() {
		String input = "She has patient when she teach me difficult word";
		List<Token> outputTokens = tagger.tagSentence(Tokenizer.tokenizeInput(input));
		
		String taggedSentence = Tokenizer.getTaggedSentence(outputTokens);
		
		assertEquals("(She)_PRP (has)_HVZ (patient)_JJ (when)_WRB (she)_PRP (teach)_VB (me)_PRP (difficult)_JJ (word)_NN", taggedSentence);
	}
	
	@Test
	public void testBackoff() {
		String input = "He is a nice person";
		List<Token> outputTokens = tagger.tagSentence(Tokenizer.tokenizeInput(input));
		
		String taggedSentence = Tokenizer.getTaggedSentence(outputTokens);
		
		assertEquals("(He)_PRP (is)_BEZ (a)_Z (nice)_NN (person)_NN", taggedSentence);
	}
}