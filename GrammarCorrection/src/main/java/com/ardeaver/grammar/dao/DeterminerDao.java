package com.ardeaver.grammar.dao;

import java.util.List;

import com.ardeaver.grammar.dto.DeterminerDto;
import com.ardeaver.grammar.dto.HasDeterminerDto;
import com.ardeaver.grammar.dto.IndefiniteDto;

/**
 * DAO class for querying the determiner database
 * 
 * @author Andrew Deaver
 * @version 1.0
 * @since 2017-03-21
 */
public class DeterminerDao extends SQLClient {
	
	// ***** CLASS VARIABLES *****
	private static final String NO_DETERMINER_QUERY = "SELECT * FROM noDeterminer WHERE word=?";
	
	private static final String DETERMINER_QUERY = "SELECT * FROM determiner WHERE word=? ORDER BY count DESC";

	private static final String INDEFINITE_QUERY = "SELECT * FROM indefiniteWord WHERE word=? ORDER BY count DESC";
	// ***** END CLASS VARIABLES *****
	
	/**
	 * Constructor
	 */
	public DeterminerDao() {
		super(new DeterminerManager());
	}
	
	/**
	 * Get a count for how many times a word occurs without a determiner
	 * 
	 * @param word The word to search
	 * @return A list of objects containing information about whether the word occurs without a determiner
	 */
	public List<DeterminerDto> getNoDeterminers(String word) {
		String[] params = {word};
		String[] columns = {"word", "count"};
		return super.executeQuery(NO_DETERMINER_QUERY, params, columns, columns, DeterminerDto.class);
	}
	
	/**
	 * Get a list of how many times a word occurs with various determiners
	 * 
	 * @param word The word to query
	 * @return A list of determiners and counts
	 */
	public List<HasDeterminerDto> getDeterminers(String word) {
		String[] params = {word};
		String[] columns = {"word", "count", "determiner"};
		return super.executeQuery(DETERMINER_QUERY, params, columns, columns, HasDeterminerDto.class);
	}
	
	/**
	 * A list of indefinite determiners for a given word
	 * 
	 * @param word The word to query
	 * @return List of indefinite articles with counts
	 */
	public List<IndefiniteDto> getCorrectIndefinite(String word) {
		String[] params = {word};
		String[] columns = {"indefinite", "word"};
		return super.executeQuery(INDEFINITE_QUERY, params, columns, columns, IndefiniteDto.class);
	}
	
}
