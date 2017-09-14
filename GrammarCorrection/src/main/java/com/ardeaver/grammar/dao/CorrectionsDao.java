package com.ardeaver.grammar.dao;

import java.util.List;

import com.ardeaver.grammar.web.models.CorrectionModel;

public class CorrectionsDao extends SQLClient {

	private static final String INSERT_CORRECTION = "INSERT INTO userCorrections (userId, original, corrected) VALUES(?, ?, ?)";
	private static final String GET_USER_CORRECTIONS = "SELECT * FROM userCorrections WHERE userId=? ORDER BY timestamp DESC";
	
	public CorrectionsDao() {
		super(new CorrectionsManager());
	}

	public void insertCorrection(CorrectionModel correction) {
		String[] params = {correction.getUserId(), correction.getOriginalSentence(), correction.getCorrectedSentence()};
		super.executeInsertQuery(INSERT_CORRECTION, params);
	}
	
	public List<CorrectionModel> getUserCorrections(String userId) {
		String[] params = {userId};
		String[] columns = {"userId", "original", "corrected"};
		String[] columnMappings = {"userId", "originalSentence", "correctedSentence"};
		return super.executeQuery(GET_USER_CORRECTIONS, params, columns, columnMappings, CorrectionModel.class);
	}
}
