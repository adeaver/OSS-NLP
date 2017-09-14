package com.ardeaver.grammar.dao;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.ardeaver.grammar.dto.CountBigram;

public class NGramsDaoTest {
	private BigramsDao dao = new BigramsDao();
	
	@Test
	public void testCanGetBigrams() {
		List<CountBigram> bigrams = dao.getBigramForWords("she", "has");
		assertTrue(bigrams.size() > 0);
	}
}
