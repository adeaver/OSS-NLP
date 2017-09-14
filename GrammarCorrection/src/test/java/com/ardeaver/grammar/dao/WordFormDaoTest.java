package com.ardeaver.grammar.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class WordFormDaoTest {
	private WordFormDao dao;
	
	public WordFormDaoTest() {
		dao = new WordFormDao();
	}
	
	@Test
	public void testLemma() {
		String lemma = dao.getLemma("running");
		
		assertEquals("run", lemma);
	}
	
	@Test
	public void testPOS() {
		String word = dao.getWordForPOS("run", "VB", "VBG");
		
		assertEquals("running", word);
	}
	
	@Test
	public void testForms() {
		List<String> forms = dao.getLemmaPOS("runs");
		
		assertTrue(forms.contains("NN"));
		assertTrue(forms.contains("VB"));
	}
}
