package com.ardeaver.grammar.phrasal;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ardeaver.grammar.preprocessing.Token;
import com.ardeaver.grammar.preprocessing.Tokenizer;

public class PhrasalCorrectorTest {
	private PhrasalVerbCorrectionService phrasalCorrector;
	
	public PhrasalCorrectorTest() {
		phrasalCorrector = new PhrasalVerbCorrectionService();
	}
	
	@Test
	public void testCorrection() {
		//String input = "share_VB with_IN me_PRP knowledge_NN";
		List<Token> input = new ArrayList<Token>();
		input.add(new Token("share"));
		input.add(new Token("with"));
		input.add(new Token("him"));
		input.add(new Token("knowledge"));
		
		input.get(0).setPartOfSpeech("VB");
		input.get(1).setPartOfSpeech("IN");
		input.get(2).setPartOfSpeech("PRP");
		input.get(3).setPartOfSpeech("NN");
		
		List<Token> output = phrasalCorrector.correctInput(input);
		
		assertEquals("share knowledge with him", Tokenizer.getSentence(output));
	}
	
	@Test
	public void testCorrection2() {
		//String input = "Thank_VB you_PRP for_IN sharing_VBG with_IN me_PRP website_NN";
		List<Token> input = new ArrayList<Token>();
		input.add(new Token("Thank"));
		input.add(new Token("you"));
		input.add(new Token("for"));
		input.add(new Token("sharing"));
		input.add(new Token("with"));
		input.add(new Token("him"));
		input.add(new Token("website"));
		
		input.get(0).setPartOfSpeech("VB");
		input.get(1).setPartOfSpeech("PRP");
		input.get(2).setPartOfSpeech("IN");
		input.get(3).setPartOfSpeech("VBG");
		input.get(4).setPartOfSpeech("IN");
		input.get(5).setPartOfSpeech("PRP");
		input.get(6).setPartOfSpeech("NN");
		
		List<Token> output = phrasalCorrector.correctInput(input);
		
		assertEquals("Thank you for share website with him", Tokenizer.getSentence(output));
	}
	
	@Test
	public void testPhrasalCorrectionNoPrep() {
		//String input = "Can_MD you_PRP share_VB me_PRP diagram_NN";
		List<Token> input = new ArrayList<Token>();
		input.add(new Token("Can"));
		input.add(new Token("you"));
		input.add(new Token("share"));
		input.add(new Token("me"));
		input.add(new Token("diagram"));
		
		input.get(0).setPartOfSpeech("MD");
		input.get(1).setPartOfSpeech("PRP");
		input.get(2).setPartOfSpeech("VB");
		input.get(3).setPartOfSpeech("PRP");
		input.get(4).setPartOfSpeech("NN");
		
		List<Token> output = phrasalCorrector.correctInput(input);
		
		assertEquals( "Can you share me diagram", Tokenizer.getSentence(output));
	}
	
	@Test
	public void testSingleNounParticle() {
		//String input = "Can_MD you_PRP please_VB wake_VB up_RP him_PRP ";
		List<Token> input = new ArrayList<Token>();
		input.add(new Token("Can"));
		input.add(new Token("you"));
		input.add(new Token("please"));
		input.add(new Token("wake"));
		input.add(new Token("up"));
		input.add(new Token("him"));
		
		input.get(0).setPartOfSpeech("MD");
		input.get(1).setPartOfSpeech("PRP");
		input.get(2).setPartOfSpeech("VB");
		input.get(3).setPartOfSpeech("VB");
		input.get(4).setPartOfSpeech("RP");
		input.get(5).setPartOfSpeech("PRP");
		
		List<Token> output = phrasalCorrector.correctInput(input);
		
		assertEquals("Can you please wake him up", Tokenizer.getSentence(output));
	}
	
	@Test
	public void testSingleNounParticle2() {
		//String input = "Stretch_VB it_PRP across_RP";
		List<Token> input = new ArrayList<Token>();
		input.add(new Token("Stretch"));
		input.add(new Token("it"));
		input.add(new Token("across"));
				
		input.get(0).setPartOfSpeech("VB");
		input.get(1).setPartOfSpeech("PRP");
		input.get(2).setPartOfSpeech("RP");
		
		List<Token> output = phrasalCorrector.correctInput(input);
		
		assertEquals("Stretch it across", Tokenizer.getSentence(output));
	}
	
	@Test
	public void testNonParticle() {
		//String input = "The_DT show_NN takes_VBZ place_NN in_IN a_Z theater_NN";
		List<Token> input = new ArrayList<Token>();
		input.add(new Token("The"));
		input.add(new Token("show"));
		input.add(new Token("takes"));
		input.add(new Token("place"));
		input.add(new Token("in"));
		input.add(new Token("a"));
		input.add(new Token("theater"));
		
		input.get(0).setPartOfSpeech("DT");
		input.get(1).setPartOfSpeech("NN");
		input.get(2).setPartOfSpeech("VBZ");
		input.get(3).setPartOfSpeech("NN");
		input.get(4).setPartOfSpeech("IN");
		input.get(5).setPartOfSpeech("Z");
		input.get(6).setPartOfSpeech("NN");
		
		List<Token> output = phrasalCorrector.correctInput(input);
		
		assertEquals(Tokenizer.getSentence(input), Tokenizer.getSentence(output));
	}
}
