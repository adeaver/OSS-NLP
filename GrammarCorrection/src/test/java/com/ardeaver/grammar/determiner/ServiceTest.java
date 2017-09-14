package com.ardeaver.grammar.determiner;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.ardeaver.grammar.preprocessing.Token;
import com.ardeaver.grammar.preprocessing.Tokenizer;
import com.ardeaver.grammar.test.util.TestTokenizer;

public class ServiceTest {
	private DeterminerService determinerService;
	
	public ServiceTest() {
		determinerService = new DeterminerService();
	}
	
	@Test
	public void testInsertion() {
		List<Token> input = TestTokenizer.tokenizeInput("She_PRP walks_VBZ to_TO district_NN");
		List<Token> output = determinerService.correctInput(input);
		
		assertEquals("She walks to the district", Tokenizer.getSentence(output));
	}
	
	@Test
	public void testDeletion() {
		List<Token> input = TestTokenizer.tokenizeInput("The_DT She_PRP walks_VBZ to_TO district_NN");
		List<Token> output = determinerService.correctInput(input);
		
		assertEquals("She walks to the district", Tokenizer.getSentence(output));
	}
	
	@Test
	public void testReplacement() {
		List<Token> input = TestTokenizer.tokenizeInput("She_PRP walks_VBZ to_TO an_Z district_NN");
		List<Token> output = determinerService.correctInput(input);
		
		assertEquals("She walks to the district", Tokenizer.getSentence(output));
	}
	
	@Test
	public void testInsertion2() {
		List<Token> input = TestTokenizer.tokenizeInput("In_IN modern_JJ digital_JJ world_NN");
		List<Token> output = determinerService.correctInput(input);
		
		assertEquals("In the modern digital world", Tokenizer.getSentence(output));
	}
	
	@Test
	public void testCleanup() {
		List<Token> input = TestTokenizer.tokenizeInput("He_PRP was_BED an_Z unique_JJ explorer_NN");
		determinerService.cleanUp(input);
		
		assertEquals("He was a unique explorer", Tokenizer.getSentence(input));
	}
	
	@Test
	public void testCleanup2() {
		List<Token> input = TestTokenizer.tokenizeInput("He_PRP was_BED a_Z excellent_JJ person_NN");
		determinerService.cleanUp(input);
		
		assertEquals("He was an excellent person", Tokenizer.getSentence(input));
	}
}
