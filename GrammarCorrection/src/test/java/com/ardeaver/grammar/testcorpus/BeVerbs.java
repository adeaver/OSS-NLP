package com.ardeaver.grammar.testcorpus;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

import com.ardeaver.grammar.pipeline.Corrector;

public class BeVerbs {
	
	private Corrector corrector;
	
	public BeVerbs() {
		corrector = new Corrector();
	}
	
	@Test
	public void testBeVerbs() {
		String input = "population aging is a phenomenon observed exclusively in advanced civilizations";
		String correction = corrector.correct(input).getSentence();
		
		System.out.println(correction);
		
		assertFalse(correction.contains("'s"));
	}
}
