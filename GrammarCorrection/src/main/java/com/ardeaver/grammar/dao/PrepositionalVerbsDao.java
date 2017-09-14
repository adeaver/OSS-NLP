package com.ardeaver.grammar.dao;

public class PrepositionalVerbsDao extends PrepositionalTranslationDao {
	private static final String VERIFY_VERB_PREPOSITION = "SELECT * FROM verbs WHERE lemma1=? AND lemma2=? AND prep=?";
	private static final String GET_VERB_TRANSLATION = "SELECT * FROM verbs WHERE lemma1=? AND lemma2=? ORDER BY count DESC LIMIT 1";

	public PrepositionalVerbsDao() {
		super(VERIFY_VERB_PREPOSITION, GET_VERB_TRANSLATION);
	}
}
