package com.ardeaver.grammar.dao;

import java.util.List;

import com.ardeaver.grammar.web.models.BetaInvite;

public class BetaInviteDao extends SQLClient {
	
	private static final String GET_BETA_INVITES = "SELECT * FROM betaInvites WHERE betakey=?";
	private static final String DELETE_BETA_INVITES = "DELETE FROM betaInvites WHERE betakey=?";
	private static final String INSERT_BETA_INVITES = "INSERT INTO betaInvites(email, betakey) VALUES(?, ?)";

	public BetaInviteDao() {
		super(new BetaInviteManager());
	}

	public BetaInvite getBetaInviteForKey(String key) {
		String[] params = {key};
		String[] columns = {"email", "betakey"};
		String[] columnMappings = {"email", "key"};
		List<BetaInvite> invites = super.executeQuery(GET_BETA_INVITES, params, columns, columnMappings, BetaInvite.class);
		return invites.size() > 0 ? invites.get(0) : null;
	}
	
	public void deleteBetaInvite(String key) {
		String[] params = {key};
		super.executeInsertQuery(DELETE_BETA_INVITES, params);
	}
	
	public void insertBetaInvite(String email, String key) {
		String[] params = {email, key};
		super.executeInsertQuery(INSERT_BETA_INVITES, params);
	}
}
