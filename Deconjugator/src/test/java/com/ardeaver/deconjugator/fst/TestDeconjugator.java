package com.ardeaver.deconjugator.fst;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.ardeaver.deconjugator.util.DeconstructedVerb;

public class TestDeconjugator {
	
	@Test
	public void testVerb() {
		DeconstructedVerb verb = RegularVerbFST.unconjugate("hablas");
		
		assertTrue("habl".equals(verb.getRoot()));
		assertTrue(verb.getEndings()[0].equals("-AR"));
	}
	
	@Test
	public void testYoForm() {
		DeconstructedVerb verb = RegularVerbFST.unconjugate("hablo");
		
		assertTrue("habl".equals(verb.getRoot()));
		assertTrue(verb.getEndings().length == 3);
	}
}
