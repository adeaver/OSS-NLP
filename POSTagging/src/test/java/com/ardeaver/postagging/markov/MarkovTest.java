package com.ardeaver.postagging.markov;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class MarkovTest {
	private MarkovImpl markov;
	
	@Before
	public void setup() {
		markov = new MarkovImpl();
	}
	
	@Test
	public void testPartOfSpeechTag() {
		String tagged = markov.tagSentence("How are you");
		System.out.println(tagged);
		
		assertEquals(3, tagged.split("\\s+").length);
	}
	
	@Test
	public void testLongerPartOfSpeechTag() {
		String tagged = markov.tagSentence("I want to go to sleep early tonight");
		System.out.println(tagged);
		
		assertEquals(8, tagged.split("\\s+").length);
	}
	
	@Test
	public void testProperNouns() {
		String tagged = markov.tagSentence("President Obama is the current president.");
		System.out.println(tagged);
		
		assertEquals(6, tagged.split("\\s+").length);
	}
	
	@Test
	public void testIncorrect() {
		String tagged = markov.tagSentence("She has patient when she teach me difficult word.");
		System.out.println(tagged);
		
		assertEquals(9, tagged.split("\\s+").length);
	}
	
	@Test
	public void testPhrasalVerbs() {
		String tagged = markov.tagSentence("But won't you take care of me when I'm old");
		System.out.println(tagged);
		
		assertEquals(10, tagged.split("\\s+").length);
	}
}
