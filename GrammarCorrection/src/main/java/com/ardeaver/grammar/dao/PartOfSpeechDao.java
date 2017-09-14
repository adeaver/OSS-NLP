package com.ardeaver.grammar.dao;

import java.util.List;

import com.ardeaver.grammar.dto.CountBigram;

/**
 * DAO class for interacting with the part of speech database
 * 
 * @author Andrew Deaver
 * @version 1.0
 * @since 2017-03-21
 */
public class PartOfSpeechDao extends SQLClient {
	
	// ***** CLASS VARIABLES *****
	private static final String GET_BIGRAMS = "SELECT * FROM bigrams WHERE pos1=? AND pos2=?";
	private static final String GET_EMISSION = "SELECT * FROM emissions WHERE word=?";
	private static final String GET_PARTS_OF_SPEECH = "SELECT * FROM parts_of_speech";
	
	private static final String GET_TOTAL_BIGRAM = "SELECT SUM(count) AS sum FROM bigrams WHERE pos1=?";
	private static final String GET_TOTAL_EMISSION = "SELECT SUM(count) AS sum FROM emissions WHERE word=?";
	
	private static final String GET_FREQUENT_TRANSITION = "SELECT * FROM bigrams WHERE pos1=? ORDER BY count DESC";
	private static final String GET_FREQUENT_EMISSION = "SELECT * FROM emissions WHERE word=? ORDER BY count DESC";
	// ***** END CLASS VARIABLES *****
	
	/**
	 * Constructor
	 */
	public PartOfSpeechDao() {
		super(new PartOfSpeechManager());
	}
	
	/**
	 * Returns transition with count based on the parts of speech
	 * 
	 * @param pos1 The first part of speech
	 * @param pos2 The second part of speech
	 * @return A count bigram containing the transition
	 */
	public CountBigram getBigrams(String pos1, String pos2) {
		String[] params = {pos1, pos2};
		String[] columns = {"pos1", "pos2", "count"};
		String[] columnMappings = {"firstWord", "lastWord", "count"};
		List<CountBigram> results = super.executeQuery(GET_BIGRAMS, params, columns, columnMappings, CountBigram.class);
		return results.size() > 0 ? results.get(0) : null; 
	}
	
	/**
	 * Returns a list containing all of the emission probabilities given a word
	 * 
	 * @param word The word to query against
	 * @return A list of emissions
	 */
	public List<CountBigram> getEmission(String word) {
		String[] params = {word};
		String[] columns = {"word", "pos", "count"};
		String[] columnMappings = {"firstWord", "lastWord", "count"};
		List<CountBigram> results = super.executeQuery(GET_EMISSION, params, columns, columnMappings, CountBigram.class);
		return results;
	}
	
	/**
	 * Gets a list of all the parts of speech
	 * 
	 * @return A list of all parts of speech
	 */
	public List<String> getPartsOfSpeech() {
		String[] params = {};
		String[] columns = {"pos"};
		List<String> results = super.executeListStringQuery(GET_PARTS_OF_SPEECH, params, columns);
		
		return results;
	}
	
	/**
	 * Gets the total amount of bigrams which start with a given part of speech
	 * 
	 * @param pos1 The part of speech that starts the transition
	 * @return The total number of transitions
	 */
	public int getTotalForBigram(String pos1) {
		String[] params = {pos1};
		String[] columns = {"sum"};
		int count = super.executeCountQuery(GET_TOTAL_BIGRAM, params, columns);
		return count > 0 ? count : 0;
	}
	
	/**
	 * The total amount of emissions given a word
	 * 
	 * @param word The word to count emissions for
	 * @return The number of emissions
	 */
	public int getTotalForEmission(String word) {
		String[] params = {word};
		String[] columns = {"sum"};
		int count = super.executeCountQuery(GET_TOTAL_EMISSION, params, columns);
		return count > 0 ? count : 0;
	}
	
	/**
	 * Returns the most common transition that starts with a given part of speech
	 * 
	 * @param pos1 The part of speech to start the transition
	 * @return The bigram containing the most common transition
	 */
	public CountBigram getMostCommonTransition(String pos1) {
		String[] params = {pos1};
		String[] columns = {"pos1", "pos2", "count"};
		String[] columnMappings = {"firstWord", "lastWord", "count"};
		List<CountBigram> results = super.executeQuery(GET_FREQUENT_TRANSITION, params, columns, columnMappings, CountBigram.class);
		return results.size() > 0 ? results.get(0) : null;
	}
	
	/**
	 * Gets the most common emission for a word
	 * 
	 * @param word The word to query
	 * @return The bigram containing the most common emission
	 */
	public CountBigram getMostCommonEmission(String word) {
		String[] params = {word};
		String[] columns = {"word", "pos", "count"};
		String[] columnMappings = {"firstWord", "lastWord", "count"};
		List<CountBigram> results = super.executeQuery(GET_FREQUENT_EMISSION, params, columns, columnMappings, CountBigram.class);
		return results.size() > 0 ? results.get(0) : null;
	}
}
