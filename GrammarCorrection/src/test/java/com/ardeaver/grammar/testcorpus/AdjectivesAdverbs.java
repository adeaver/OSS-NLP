package com.ardeaver.grammar.testcorpus;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.ardeaver.grammar.pipeline.Corrector;

public class AdjectivesAdverbs {
	private Corrector corrector;
	
	public AdjectivesAdverbs() {
		corrector = new Corrector();
	}
	
	@Test
	public void testAdjectives1() {
		String input = "in this fast moving society technology is always improving to catch up the fast pace of this world and most surveillance technology is used by the police or other army usage to track people down";
		String correction = corrector.correct(input).getSentence();
		
		System.out.println(correction);
		
		assertTrue(correction.contains("is always improving"));
	}
	
	@Test
	public void testAdjectives2() {
		String input = "although the surveillance technology is an issue in invading the human privacy it has also brought us lots of benefits and security";
		String correction = corrector.correct(input).getSentence();
		
		System.out.println(correction);
		
		assertTrue(correction.contains("has also brought"));
	}
	
	@Test
	public void testAdjectives3() {
		String input = "for using as a surveillance tool it could also be used in terms of surveys and statistics";
		String correction = corrector.correct(input).getSentence();
		
		System.out.println(correction);
		
		assertTrue(correction.contains("could also be used"));
	}
	
	@Test
	public void testAdjectives4() {
		String input = "by following the trend of improvement in technology people can now easily tracked by using rfid by simply implant a rfid tags on human body";
		String correction = corrector.correct(input).getSentence();
		
		System.out.println(correction);
		
		assertTrue(correction.contains("can now easily track"));
	}
	
	@Test
	public void testAdjectives5() {
		String input = "however disadvantages have also been gradually disappeared as with the fruits people are enjoying";
		String correction = corrector.correct(input).getSentence();
		
		System.out.println(correction);
		
		assertTrue(correction.contains("have also been gradually disappeared"));
	}
	
	@Test
	public void testAdjectives6() {
		String input = "it should therefore not be used to track the population in order to protect people 's privacy and the idea of democracy";
		String correction = corrector.correct(input).getSentence();
		
		assertTrue(correction.contains("should therefore not be used"));
	}
	
	@Test
	public void testAdjectives7() {
		String input = "however the using of surveillance technology is usually done secretly and a debate has been aroused today that whether surveillance technology should be abandoned";
		String correction = corrector.correct(input).getSentence();
		
		System.out.println(correction);
		
		assertTrue(correction.contains("is usually done"));
	}
	
	@Test
	public void testAdjectives8() {
		String input = "the weak is meat and the strong eat it does not apply nowadays";
		String correction = corrector.correct(input).getSentence();
		
		System.out.println(correction);
		
		assertTrue(correction.contains("does not apply"));
	}
	
	@Test
	public void testAdjectives9() {
		String input = "has I not doing it yet";
		String correction = corrector.correct(input).getSentence();
		
		System.out.println(correction);
		
		assertTrue(correction.contains("not done"));
	}
} 