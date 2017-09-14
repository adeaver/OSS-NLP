package com.ardeaver.grammar.dao;

import java.util.List;

import com.ardeaver.grammar.dto.CountSingleNounPhrasal;
import com.ardeaver.grammar.dto.CountTwoNounPhrasal;

/**
 * The DAO class for interacting with the phrasal verbs database
 * 
 * @author Andrew Deaver
 * @version 1.0
 * @since 2017-03-21
 */
public class PhrasalDao extends SQLClient {
	// ***** INSTANCE VARIABLES *****
	private WordFormDao lemmaDao;
	// ***** END INSTANCE VARIABLES *****
	
	// ***** CLASS VARIABLES *****
	private static final String TWO_NOUN_QUERY = "SELECT * FROM twoNounPrep WHERE verbLemma=? AND noun1=? and noun2=? ORDER BY count DESC";
	
	private static final String TWO_NOUN_BACKOFF = "SELECT * FROM twoNounPrep WHERE verbLemma=? AND noun1='NN' AND noun2=? ORDER BY count DESC";
	
	private static final String TWO_NOUN_BACKOFF2 = "SELECT * FROM twoNounPrep WHERE verbLemma=? AND noun1=? and noun2='NN' ORDER BY count DESC";
	
	private static final String PARTICLE_MID = "SELECT * FROM particleMid WHERE verbLemma=? AND noun=? ORDER BY count DESC";
	
	private static final String PARTICLE_MID_BACKOFF = "SELECT * FROM particleMid WHERE verbLemma=? AND noun='NN' ORDER BY count DESC";
	
	private static final String PARTICLE_END = "SELECT * FROM particleEnd WHERE verbLemma=? AND noun=? ORDER BY count DESC";
	
	private static final String PARTICLE_END_BACKOFF = "SELECT * FROM particleEnd WHERE verbLemma=? AND noun='NN' ORDER BY count DESC"; 
	// ***** END CLASS VARIABLES *****
	
	/**
	 * Constructor
	 */
	public PhrasalDao() {
		super(new PhrasalManager());
		lemmaDao = new WordFormDao();
	}
	
	public List<CountTwoNounPhrasal> getPhrasalTwoNoun(String verb, String noun1, String noun2) {
		String verbLemma = lemmaDao.getLemma(verb);
		String noun1Lemma = lemmaDao.getLemma(noun1);
		String noun2Lemma = lemmaDao.getLemma(noun2);
		
		String[] params = {verbLemma, noun1Lemma, noun2Lemma};
		String[] columns = {"verbLemma", "noun1", "noun2", "prep", "count"};
		String[] columnMappings = {"verb", "noun1", "noun2", "prep", "count"};
		return super.executeQuery(TWO_NOUN_QUERY, params, columns, columnMappings, CountTwoNounPhrasal.class);
	}
	
	public List<CountTwoNounPhrasal> getPhrasalNoun2BackOff(String verb, String noun) {
		String verbLemma = lemmaDao.getLemma(verb);
		String nounLemma = lemmaDao.getLemma(noun);
		
		String[] params = {verbLemma, nounLemma};
		String[] columns = {"verbLemma", "noun1", "noun2", "prep", "count"};
		String[] columnMappings = {"verb", "noun1", "noun2", "prep", "count"};
		return super.executeQuery(TWO_NOUN_BACKOFF, params, columns, columnMappings, CountTwoNounPhrasal.class);
	}
	
	public List<CountTwoNounPhrasal> getPhrasalNoun1Backoff(String verb, String noun) {
		String verbLemma = lemmaDao.getLemma(verb);
		String nounLemma = lemmaDao.getLemma(noun);
		
		String[] params = {verbLemma, nounLemma};
		String[] columns = {"verbLemma", "noun1", "noun2", "prep", "count"};
		String[] columnMappings = {"verb", "noun1", "noun2", "prep", "count"};
		return super.executeQuery(TWO_NOUN_BACKOFF2, params, columns, columnMappings, CountTwoNounPhrasal.class);
	}
	
	public List<CountSingleNounPhrasal> getParticleMid(String verb, String noun) {
		String verbLemma = lemmaDao.getLemma(verb);
		String nounLemma = lemmaDao.getLemma(noun);
		
		String[] params = {verbLemma, nounLemma};
		String[] columns = {"verbLemma", "noun", "prep", "count"};
		String[] columnMappings = {"verb", "noun", "prep", "count"};
		return super.executeQuery(PARTICLE_MID, params, columns, columnMappings, CountSingleNounPhrasal.class);
	}
	
	public List<CountSingleNounPhrasal> getParticleMidBackoff(String verb) {
		String verbLemma = lemmaDao.getLemma(verb);
		
		String[] params = {verbLemma};
		String[] columns = {"verbLemma", "noun", "prep", "count"};
		String[] columnMappings = {"verb", "noun", "prep", "count"};
		return super.executeQuery(PARTICLE_MID_BACKOFF, params, columns, columnMappings, CountSingleNounPhrasal.class);
	}
	
	public List<CountSingleNounPhrasal> getParticleEnd(String verb, String noun) {
		String verbLemma = lemmaDao.getLemma(verb);
		String nounLemma = lemmaDao.getLemma(noun);
		
		String[] params = {verbLemma, nounLemma};
		String[] columns = {"verbLemma", "noun", "prep", "count"};
		String[] columnMappings = {"verb", "noun", "prep", "count"};
		return super.executeQuery(PARTICLE_END, params, columns, columnMappings, CountSingleNounPhrasal.class);
	}
	
	public List<CountSingleNounPhrasal> getParticleEndBackoff(String verb) {
		String verbLemma = lemmaDao.getLemma(verb);
		
		String[] params = {verbLemma};
		String[] columns = {"verbLemma", "noun", "prep", "count"};
		String[] columnMappings = {"verb", "noun", "prep", "count"};
		return super.executeQuery(PARTICLE_END_BACKOFF, params, columns, columnMappings, CountSingleNounPhrasal.class);
	}
}
