package com.ardeaver.postagging.transition;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TransitionTableTest {
	private static final TransitionTable table = new TransitionTable();
	
	@Test
	public void testNounToNoun() {
		double probability = table.getTransitionProability("NN", "NN");
		assertEquals(0.087, probability, .001);
	}
	
	@Test
	public void testStartToNoun() {
		double probability = table.getTransitionProability("<s>", "NN");
		assertEquals(.04, probability, .01);
	}
	
	@Test
	public void testStartToStart() {
		double probability = table.getTransitionProability("<s>", "<s>");
		assertEquals(0, probability, 0);
	}
}
