package com.ardeaver.grammar.dao;

import java.util.List;

import com.ardeaver.grammar.dto.PrepositionalPhrase;

public class PrepositionalTranslationDao extends SQLClient {
	private String verifyQuery, translateQuery;
	
	public PrepositionalTranslationDao(String verifyQuery, String translateQuery) {
		super(new PrepositionalDBManager());
		this.verifyQuery = verifyQuery;
		this.translateQuery = translateQuery;
	}
	
	public boolean isValid(String lemma1, String lemma2, String prep) {
		String[] params = {lemma1, lemma2, prep};
		String[] columns = {"word1", "word2", "prep", "lemma1", "lemma2"};
		String[] columnMappings = {"word1", "word2", "prep", "lemma1", "lemma2"};
		List<PrepositionalPhrase> prepositionalPhrases = super.executeQuery(verifyQuery, params, columns, columnMappings, PrepositionalPhrase.class);
		return prepositionalPhrases.size() > 0;
	}
	
	public PrepositionalPhrase getTranslation(String lemma1, String lemma2) {
		String[] params = {lemma1, lemma2};
		String[] columns = {"word1", "word2", "prep", "lemma1", "lemma2"};
		String[] columnMappings = {"word1", "word2", "prep", "lemma1", "lemma2"};
		List<PrepositionalPhrase> prepositionalPhrases = super.executeQuery(translateQuery, params, columns, columnMappings, PrepositionalPhrase.class);
		return prepositionalPhrases.size() > 0 ? prepositionalPhrases.get(0) : null;
	}
}
