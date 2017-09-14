package com.ardeaver.grammar.sva;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.ardeaver.grammar.preprocessing.Token;
import com.ardeaver.grammar.test.util.TestTokenizer;

public class SubjectVerbAgreementCorrectorTest {
	private SubjectVerbAgreementCorrectionService svaCorrector = new SubjectVerbAgreementCorrectionService();
	
	@Test
	public void testSVACorrection() {
		List<Token> input = TestTokenizer.tokenizeInput("She_PRP work_VB me_PPO difficult_JJ word_NN");
		List<Token> correctString = svaCorrector.correctInput(input);
		
		System.out.println(TestTokenizer.getSentence(correctString));
		
		assertEquals("She works me difficult word", TestTokenizer.getSentence(correctString));
	}
	
	@Test
	public void testSVACorrectionWithAdverb() {
		List<Token> input = TestTokenizer.tokenizeInput("She_PRP have_HV really_RB now_RB work_VB for_IN me_PPO twice_RB");
		List<Token> correctString = svaCorrector.correctInput(input);
		
		System.out.println(TestTokenizer.getSentence(correctString));
		
		assertEquals("She has really now worked for me twice", TestTokenizer.getSentence(correctString));
	}
	
	@Test
	public void testSecondCorrection() {
		List<Token> input = TestTokenizer.tokenizeInput("he_PRP said_VBD that_CS he_PRP want_VB it_PPS");
		List<Token> correctString = svaCorrector.correctInput(input);
		
		assertEquals("he said that he wants it", TestTokenizer.getSentence(correctString));
	}
	
	@Test
	public void testNoCorrection() {
		List<Token> input = TestTokenizer.tokenizeInput("he_PRP said_VBD that_CS it_PPS did_DOD");
		List<Token> correctString = svaCorrector.correctInput(input);
		
		assertEquals("he said that it did", TestTokenizer.getSentence(correctString));
	}
	
	@Test
	public void testInfinitive() {
		List<Token> input = TestTokenizer.tokenizeInput("he_PRP have_HV return_VBZ to_TO the_DT store_NN");
		List<Token> correctString = svaCorrector.correctInput(input);
		
		System.out.println(TestTokenizer.getSentence(correctString));
		
		assertEquals("he has returned to the store", TestTokenizer.getSentence(correctString));
	}
	
	@Test
	public void testMultiCorrection() {
		List<Token> input = TestTokenizer.tokenizeInput("he_PRP believe_VB that_CS it_PRP have_HV");
		List<Token> correctString = svaCorrector.correctInput(input);
		
		System.out.println(TestTokenizer.getSentence(correctString));
		
		assertEquals("he believes that it has", TestTokenizer.getSentence(correctString));
	}
	
	@Test
	public void testExtraVerbs() {
		List<Token> input = TestTokenizer.tokenizeInput("She_PRP appear_VB at_IN the_DT entertainment_NN district_NN and_CC want_VB to_TO see_VB show_NN");
		List<Token> correctString = svaCorrector.correctInput(input);
		
		System.out.println(TestTokenizer.getSentence(correctString));
		
		assertEquals("She appears at the entertainment district and want to see show", TestTokenizer.getSentence(correctString));
	}
	
	@Test
	public void testDoubleNoun() {
		List<Token> input = TestTokenizer.tokenizeInput("She_PRP and_CC I_PRP has_HVZ a_DT dog_NN");
		List<Token> correctString = svaCorrector.correctInput(input);
		
		assertEquals("She and I have a dog", TestTokenizer.getSentence(correctString));
	}
	
	@Test
	public void testThreeVerbs() {
		List<Token> input = TestTokenizer.tokenizeInput("He_PRP will_MD has_HVZ work_VB it_PRP");
		List<Token> correctString = svaCorrector.correctInput(input);
		
		System.out.println(TestTokenizer.getSentence(correctString));
		
		assertEquals("He will have worked it", TestTokenizer.getSentence(correctString));
	}
	
	@Test
	public void testSubPhrase() {
		List<Token> input = TestTokenizer.tokenizeInput("The_DT students_NN that_IN he_PRP teach_VB will_MD worked_VBD");
		List<Token> correctString = svaCorrector.correctInput(input);
		
		System.out.println(TestTokenizer.getSentence(correctString));
		
		assertEquals("The students that he teaches will work", TestTokenizer.getSentence(correctString));
	}
	
	@Test
	public void testAuxPhrase() {
		List<Token> input = TestTokenizer.tokenizeInput("Have_HV he_PRP use_VB it_PRP");
		List<Token> correctString = svaCorrector.correctInput(input);
		
		System.out.println(TestTokenizer.getSentence(correctString));
		
		assertEquals("has he used it", TestTokenizer.getSentence(correctString));
	}
	
	@Test
	public void testAuxPhrase2() {
		List<Token> input = TestTokenizer.tokenizeInput("will_MD the_DT banks_NN are_BEZ open_VBN");
		List<Token> correctString = svaCorrector.correctInput(input);
		
		System.out.println(TestTokenizer.getSentence(correctString));
		
		assertEquals("will the banks be open", TestTokenizer.getSentence(correctString));
	}
	
	@Test
	public void testI() {
		List<Token> input = TestTokenizer.tokenizeInput("I_PRP is_BEZ good_JJ");
		List<Token> correctString = svaCorrector.correctInput(input);
		
		System.out.println(TestTokenizer.getSentence(correctString));
		
		assertEquals("I am good", TestTokenizer.getSentence(correctString));
	}
	
}
