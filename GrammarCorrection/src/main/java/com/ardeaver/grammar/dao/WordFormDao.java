package com.ardeaver.grammar.dao;

import java.util.List;

public class WordFormDao extends SQLClient {	
	private static final String GET_LEMMA = "SELECT lemma FROM lemmas WHERE word=? LIMIT 1";
	private static final String GET_POS = "SELECT word FROM lemmas WHERE lemma=(SELECT lemma FROM lemmas WHERE word=? AND pos LIKE ? LIMIT 1) AND pos=?";
	private static final String GET_LEMMA_POS = "SELECT pos FROM lemmas WHERE lemma=(SELECT lemma FROM lemmas WHERE word=? LIMIT 1) AND LENGTH(pos)=2";
	
	public WordFormDao() {
		super(new NGramsDBManager());
	}
	
	public String getLemma(String word) {
		String[] params = {word};
		String[] columns = {"lemma"};
		List<String> lemmas = super.executeListStringQuery(GET_LEMMA, params, columns);
		return lemmas.size() > 0 ? lemmas.get(0) : "";
	}
	
	public List<String> getLemmaPOS(String word) {
		String[] params = {word};
		String[] columns = {"pos"};
		return super.executeListStringQuery(GET_LEMMA_POS, params, columns);
	}
	
	public String getWordForPOS(String word, String basePos, String pos) {
		String[] params = {word, basePos, pos};
		String[] columns = {"word"};
		List<String> results = super.executeListStringQuery(GET_POS, params, columns);
		
		return results.size() > 0 ? results.get(0) : "";
	}
}
