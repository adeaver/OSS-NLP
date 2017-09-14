package com.ardeaver.grammar.testcorpus;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.ardeaver.grammar.pipeline.Correction;
import com.ardeaver.grammar.pipeline.Corrector;

public class ExistentialCorrections {
	private Corrector corrector;
	
	public ExistentialCorrections() {
		corrector = new Corrector();
	}
	
	@Test
	public void testExistentialThere() {
		String input = "there is one thing is common in the world described in many fiction chips are implanted in human and records its location history and even thoughts at that time";
		Correction corrections = corrector.correct(input);
		String correctionSentence = corrections.getSentence();
		
		System.out.println(correctionSentence);
		
		assertTrue(correctionSentence.toLowerCase().startsWith("there is"));
	}
}
