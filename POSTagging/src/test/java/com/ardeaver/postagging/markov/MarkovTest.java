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
}
