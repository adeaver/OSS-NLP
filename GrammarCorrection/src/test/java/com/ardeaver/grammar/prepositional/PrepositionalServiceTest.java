package com.ardeaver.grammar.prepositional;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ardeaver.grammar.prepositional.noun.PrepositionalNounService;
import com.ardeaver.grammar.prepositional.verb.PrepositionalVerbService;
import com.ardeaver.grammar.preprocessing.Token;
import com.ardeaver.grammar.preprocessing.Tokenizer;

public class PrepositionalServiceTest {
	private PrepositionalNounService nounService;
	private PrepositionalVerbService verbService;
	
	public PrepositionalServiceTest() {
		nounService = new PrepositionalNounService();
		verbService = new PrepositionalVerbService();
	}
	
	@Test
	public void testPhrase() {
		//String input = "ability_NN of_IN hearing_VBG";
		List<Token> input = new ArrayList<Token>();
		input.add(new Token("considered"));
		input.add(new Token("of"));
		input.add(new Token("being"));
		
		input.get(0).setPartOfSpeech("VB");
		input.get(1).setPartOfSpeech("IN");
		input.get(2).setPartOfSpeech("VBG");
		
		List<Token> noOutput = nounService.correctInput(input);
		
		assertEquals("considered of being", Tokenizer.getSentence(noOutput));
		
		List<Token> output = verbService.correctInput(input);
		
		assertEquals("considered to be", Tokenizer.getSentence(output));
	}
	
	@Test
	public void testPhrase2() {
		//String input = "ability_NN run_VB";
		List<Token> input = new ArrayList<Token>();
		input.add(new Token("period"));
		input.add(new Token("in"));
		input.add(new Token("time"));
		
		input.get(0).setPartOfSpeech("NN");
		input.get(1).setPartOfSpeech("IN");
		input.get(2).setPartOfSpeech("NN");
		
		List<Token> output = nounService.correctInput(input);
		List<Token> noOutput = verbService.correctInput(input);
		
		assertEquals("period of time", Tokenizer.getSentence(output));
		assertEquals("period in time", Tokenizer.getSentence(noOutput));
	}
	
	@Test
	public void testExtraVerb() {
		//String input = "destroyed_VBD of_IN fire_NN";
		List<Token> input = new ArrayList<Token>();
		input.add(new Token("list"));
		input.add(new Token("in"));
		input.add(new Token("the"));
		input.add(new Token("canadian"));
		
		input.get(0).setPartOfSpeech("NN");
		input.get(1).setPartOfSpeech("IN");
		input.get(2).setPartOfSpeech("DT");
		input.get(3).setPartOfSpeech("NN");
		
		List<Token> output = verbService.correctInput(input);
		List<Token> noOutput = nounService.correctInput(input);
		
		assertEquals("list in the canadian", Tokenizer.getSentence(output));
		assertEquals("list of the canadian", Tokenizer.getSentence(noOutput));
	}
	
	@Test
	public void testDontCorrect() {
		List<Token> input = new ArrayList<Token>();
		input.add(new Token("had"));
		input.add(new Token("run"));
		
		input.get(0).setPartOfSpeech("VBD");
		input.get(1).setPartOfSpeech("VBD");
		
		//List<Token> output = verbService.correctInput(input);
		//List<Token> noOutput = nounService.correctInput(input);
		
		//assertEquals("had run", Tokenizer.getSentence(output));
		//assertEquals("had run", Tokenizer.getSentence(noOutput));
	}
	
	@Test
	public void testChange() {
		List<Token> input = new ArrayList<Token>();
		input.add(new Token("want"));
		input.add(new Token("being"));
		
		input.get(0).setPartOfSpeech("VB");
		input.get(1).setPartOfSpeech("VBG");
		
		List<Token> output = verbService.correctInput(input);
		List<Token> noOutput = nounService.correctInput(input);
		
		assertEquals("want to be", Tokenizer.getSentence(output));
		assertEquals("want being", Tokenizer.getSentence(noOutput));
	}
	
	@Test
	public void testNoChange() {
		List<Token> input = new ArrayList<Token>();
		input.add(new Token("want"));
		input.add(new Token("knowledge"));
		
		input.get(0).setPartOfSpeech("VB");
		input.get(1).setPartOfSpeech("NN");
		
		List<Token> output = verbService.correctInput(input);
		List<Token> noOutput = nounService.correctInput(input);
		
		assertEquals("want knowledge", Tokenizer.getSentence(output));
		assertEquals("want knowledge", Tokenizer.getSentence(noOutput));
	}
}
