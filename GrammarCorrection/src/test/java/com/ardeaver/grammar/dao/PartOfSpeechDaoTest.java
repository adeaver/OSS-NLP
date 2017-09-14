package com.ardeaver.grammar.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.ardeaver.grammar.dto.CountBigram;

public class PartOfSpeechDaoTest {
	private PartOfSpeechDao partOfSpeechDao;
	
	public PartOfSpeechDaoTest() {
		partOfSpeechDao = new PartOfSpeechDao();
	}
	
	@Test
	public void testGetEmissionProbability() {
		List<CountBigram> bigrams = partOfSpeechDao.getEmission("person");
		CountBigram bigram = bigrams.get(0);
		
		assertEquals("person", bigram.getFirstWord());
		assertEquals("NN", bigram.getLastWord());
		assertEquals(16, bigram.getCount());
	}
	
	@Test
	public void testGetPartsOfSpeech() {
		List<String> partsOfSpeech = partOfSpeechDao.getPartsOfSpeech();
		
		assertEquals(53, partsOfSpeech.size());
	}
}
