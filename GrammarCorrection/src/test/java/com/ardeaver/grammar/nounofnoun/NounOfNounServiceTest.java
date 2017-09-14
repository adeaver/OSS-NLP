package com.ardeaver.grammar.nounofnoun;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ardeaver.grammar.preprocessing.Token;
import com.ardeaver.grammar.preprocessing.Tokenizer;

public class NounOfNounServiceTest {
	private NounOfNounService nounOfNounService;
	
	public NounOfNounServiceTest() {
		nounOfNounService = new NounOfNounService();
	}
	
	@Test
	public void testNounOfNoun() {
		//String input = "dealership_NN of_IN car_NN";
		List<Token> input = new ArrayList<Token>();
		input.add(new Token("dealership"));
		input.add(new Token("of"));
		input.add(new Token("car"));
		
		input.get(0).setPartOfSpeech("NN");
		input.get(1).setPartOfSpeech("IN");
		input.get(2).setPartOfSpeech("NN");
		
		List<Token> output = nounOfNounService.correctInput(input);
		
		assertEquals(1, output.size());
		assertEquals("car dealership", Tokenizer.getSentence(output));
	}
	
	@Test
	public void testNounOfNoun2() {
		//String input = "jurisdiction_NNS of_IN district_NNS";
		List<Token> input = new ArrayList<Token>();
		input.add(new Token("jurisdiction"));
		input.add(new Token("of"));
		input.add(new Token("district"));
		
		input.get(0).setPartOfSpeech("NNS");
		input.get(1).setPartOfSpeech("IN");
		input.get(2).setPartOfSpeech("NNS");
		
		List<Token> output = nounOfNounService.correctInput(input);
		
		assertEquals("district's jurisdiction", Tokenizer.getSentence(output));
		assertEquals("jurisdiction", output.get(0).getHeadWord());
	}
	
	@Test
	public void testNounOfNoun3() {
		//String input = "members_NN of_IN parliament_NNP";
		List<Token> input = new ArrayList<Token>();
		input.add(new Token("members"));
		input.add(new Token("of"));
		input.add(new Token("parliament"));
		
		input.get(0).setPartOfSpeech("NN");
		input.get(1).setPartOfSpeech("IN");
		input.get(2).setPartOfSpeech("NN");
		
		List<Token> output = nounOfNounService.correctInput(input);
		
		assertEquals("members of parliament", Tokenizer.getSentence(output));
	}
}
