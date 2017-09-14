package com.ardeaver.grammar.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BigramsDaoTest {
	private BigramsDao bigramsDao;
	
	public BigramsDaoTest() {
		bigramsDao = new BigramsDao();
	}
	
	@Test
	public void testBigramCounts() {
		int countFirstWord = bigramsDao.getBigramTotalForWord1("he");
		
		assertEquals(9038, countFirstWord);
	}
}
