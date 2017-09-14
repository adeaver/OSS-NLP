package com.ardeaver.grammar.dao;

public class PrepositionalNounsDao extends PrepositionalTranslationDao {
	private static final String VERIFY_NOUN_PREPOSITION = "SELECT * FROM nouns WHERE lemma1=? AND lemma2=? AND prep=?";
	private static final String GET_NOUN_TRANSLATION = "SELECT * FROM nouns WHERE lemma1=? AND lemma2=? ORDER BY count DESC LIMIT 1";

	public PrepositionalNounsDao() {
		super(VERIFY_NOUN_PREPOSITION, GET_NOUN_TRANSLATION);
	}
}
