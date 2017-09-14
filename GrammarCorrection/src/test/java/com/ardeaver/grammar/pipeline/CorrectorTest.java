package com.ardeaver.grammar.pipeline;

import org.junit.Test;

public class CorrectorTest {
	private Corrector corrector;
	
	public CorrectorTest() {
		corrector = new Corrector();
	}
	
	@Test
	public void testAllCorrections() {
		String input = "She walk to district of entertainment and request of see show";
		Correction correctedOutput = corrector.correct(input);
		
		System.out.println(correctedOutput);
		
		//assertTrue(correctedOutput.contains("She walked to the entertainment district and request to see show"));
	}
	
	@Test
	public void testAllCorrections2() {
		String input = "She come to district of entertainment and want to see show";
		Correction correctedOutput = corrector.correct(input);
		
		System.out.println(correctedOutput);
		
		//assertTrue(correctedOutput.contains("She came to the entertainment district and wanted to see show"));
		//assertTrue(correctedOutput.contains("She comes to the entertainment district and wants to see show"));
	}
	
	@Test
	public void testAllCorrections3() {
		String input = "Thank you for giving with me district of entertainment";
		Correction correctedOutput = corrector.correct(input);
		
		System.out.println(correctedOutput);
	}
	
	@Test
	public void testAllCorrections4() {
		String input = "in modern world many global issues have been appeared like global warming concept of earth village and population";
		Correction correctedOutput = corrector.correct(input);
		
		System.out.println(correctedOutput);
	}
	
	@Test
	public void testAllCorrections5() {
		String input = "for people this is a achievement but for nature it change its rule and cycle which is a big challenge faced by human being";
		Correction correctedOutput = corrector.correct(input);
		
		System.out.println(correctedOutput);
	}
	
	@Test
	public void testFunny() {
		String input = "And no I want to do not have it be a thing of the past";
		
		Correction correctedOutput = corrector.correct(input);
		
		System.out.println(correctedOutput);
	}
}
