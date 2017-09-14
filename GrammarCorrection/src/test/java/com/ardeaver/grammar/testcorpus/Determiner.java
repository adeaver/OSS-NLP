package com.ardeaver.grammar.testcorpus;

import org.junit.Test;

import com.ardeaver.grammar.pipeline.Corrector;

public class Determiner {
	private Corrector corrector;
	
	public Determiner() {
		corrector = new Corrector();
	}
	
	@Test
	public void testRemovingDeterminers() {
		String input = "the contradiction of the safety and the privacy cannot be reconciled perfectly";
		String output = corrector.correct(input).getSentence();
		
		System.out.println(output);
	}
}
