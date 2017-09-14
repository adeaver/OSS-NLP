package com.ardeaver.grammar.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.ardeaver.grammar.dto.CountTrigram;

public class TrigramsDaoTest {
	private TrigramsDao trigramsDao;
	
	public TrigramsDaoTest() {
		trigramsDao = new TrigramsDao();
	}
	
	@Test
	public void testCanGetTrigrams() {
		List<CountTrigram> countTrigrams = trigramsDao.getTrigram("also", "appeared", "in");
		
		assertEquals(1, countTrigrams.size());
		assertEquals(21, countTrigrams.get(0).getCount());
	}
}
