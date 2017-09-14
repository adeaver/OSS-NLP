package com.ardeaver.grammar.dao;

import java.util.List;

import com.ardeaver.grammar.dto.CountTrigram;

public class TrigramsDao extends SQLClient {
	
	public TrigramsDao() {
		super(new NGramsDBManager());
	}
	
	private static final String GET_TRIGRAM = "SELECT * FROM trigrams WHERE "
			+ "word1=? AND word2=? AND word3=?";
	
	public List<CountTrigram> getTrigram(String word1, String word2, String word3) {
		String[] params = {word1, word2, word3};
		String[] columns = {"word1", "word2", "word3", "count"};
		String[] columnMappings = {"firstWord", "middleWord", "lastWord", "count"};
		return super.executeQuery(GET_TRIGRAM, params, columns, columnMappings, CountTrigram.class);
	}
}
