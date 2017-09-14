package com.ardeaver.grammar.dao;

import java.util.List;

import com.ardeaver.grammar.dto.SubjectVerbAgreementDto;

public class SubjectVerbAgreementDao extends SQLClient {
	
	private static final String GET_NEW_FORM = "SELECT word2pos, SUM(count) count FROM bigrams WHERE word1pos=? AND word2pos LIKE ? GROUP BY word2pos ORDER BY count DESC";
	private static final String GET_BASED_ON_WORD = "SELECT word2pos, SUM(count) count FROM bigrams WHERE word1=? AND word2pos LIKE ? GROUP BY word2pos ORDER BY count DESC";
	private static final String GET_PRESENT_TENSE = "SELECT word2pos, SUM(count) count FROM bigrams WHERE word1pos=? AND word2pos LIKE ? AND word2pos NOT LIKE '%D' AND word2pos NOT LIKE '%N' GROUP BY word2pos ORDER BY count DESC";

	public SubjectVerbAgreementDao() {
		super(new NGramsDBManager());
	}
	
	public List<SubjectVerbAgreementDto> getForms(String wordOnePos, String wordTwoBaseForm) {
		String[] params = {wordOnePos, wordTwoBaseForm};
		String[] columns = {"word2pos", "count"};
		String[] columnMappings = {"partOfSpeech", "count"};
		return super.executeQuery(GET_NEW_FORM, params, columns, columnMappings, SubjectVerbAgreementDto.class);
	}
	
	public List<SubjectVerbAgreementDto> getPresentTenseForms(String wordOnePos, String wordTwoBaseForm) {
		String[] params = {wordOnePos, wordTwoBaseForm};
		String[] columns = {"word2pos", "count"};
		String[] columnMappings = {"partOfSpeech", "count"};
		return super.executeQuery(GET_PRESENT_TENSE, params, columns, columnMappings, SubjectVerbAgreementDto.class);
	}
	
	public List<SubjectVerbAgreementDto> getFormsBasedOnWord(String wordOne, String wordTwoBaseForm) {
		String[] params = {wordOne, wordTwoBaseForm};
		String[] columns = {"word2pos", "count"};
		String[] columnMappings = {"partOfSpeech", "count"};
		return super.executeQuery(GET_BASED_ON_WORD, params, columns, columnMappings, SubjectVerbAgreementDto.class);
	}

}
