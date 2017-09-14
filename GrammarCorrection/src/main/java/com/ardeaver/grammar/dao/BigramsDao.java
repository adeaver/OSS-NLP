package com.ardeaver.grammar.dao;

import java.util.List;

import com.ardeaver.grammar.dto.CountBigram;

/**
 * This is the DAO class used to make queries about bigrams
 * 
 * @author Andrew Deaver
 * @version 1.0
 * @since 2017-03-21
 */
public class BigramsDao extends SQLClient {
	
	/**
	 * Constructor
	 */
	public BigramsDao() {
		super(new NGramsDBManager());
	}
	
	// ***** CLASS VARIABLES *****
	
	private static final String BIGRAMS_FOR_LEMMAS = "SELECT * FROM bigrams WHERE "
			+ "word1 in (SELECT word FROM lemmas WHERE lemma=(SELECT lemma FROM lemmas WHERE word=? LIMIT 1)) AND "
			+ "word2 in (SELECT word FROM lemmas WHERE lemma=(SELECT lemma FROM lemmas WHERE word=? LIMIT 1))";
	
	private static final String BIGRAMS_FOR_WORDS = "SELECT * FROM bigrams WHERE word1=? AND word2=?";
	
	private static final String BIGRAMS_FOR_WORD1 = "SELECT * FROM bigrams WHERE word1 in (SELECT word FROM lemmas WHERE lemma=(SELECT lemma FROM lemmas WHERE word=? LIMIT 1)) AND "
			+ "word2 not in (SELECT word FROM lemmas WHERE lemma=(SELECT lemma FROM lemmas WHERE word=? LIMIT 1))";
	
	private static final String BIGRAMS_FOR_WORD2 = "SELECT * FROM bigrams WHERE word1 not in (SELECT word FROM lemmas WHERE lemma=(SELECT lemma FROM lemmas WHERE word=? LIMIT 1)) AND "
			+ "word2 in (SELECT word FROM lemmas WHERE lemma=(SELECT lemma FROM lemmas WHERE word=? LIMIT 1))";
	
	private static final String COUNT_FIRST_BIGRAM = "SELECT SUM(count) c FROM bigrams WHERE word1 in (SELECT word FROM lemmas WHERE lemma=(SELECT lemma FROM lemmas WHERE word=? LIMIT 1))";
	private static final String COUNT_LAST_BIGRAM = "SELECT SUM(count) c FROM bigrams WHERE word2 in (SELECT word FROM lemmas WHERE lemma=(SELECT lemma FROM lemmas WHERE word=? LIMIT 1))";
	// ***** END CLASS VARIABLES *****
	
	/**
	 * Returns the entry based on the bigrams containing the two words in their current order
	 * 
	 * @param word1 The first word of the bigram
	 * @param word2 The second word of the bigram
	 * @return A list of the bigrams with counts
	 */
	public List<CountBigram> getBigramForWords(String word1, String word2) {
		String[] params = {word1, word2};
		String[] columns = {"word1", "word2", "count"};
		String[] columnMappings = {"firstWord", "lastWord", "count"};
		return super.executeQuery(BIGRAMS_FOR_WORDS, params, columns, columnMappings, CountBigram.class);
	}
	
	/**
	 * Returns the entries containing the bigrams made up of any form
	 * of the word
	 * 
	 * @param word1 The first word in the bigram
	 * @param word2 The second word in the bigram
	 * @return A list of the bigrams with counts
	 */
	public List<CountBigram> getLemmaBigrams(String word1, String word2) {
		String[] params = {word1, word2};
		String[] columns = {"word1", "word2", "count"};
		String[] columnMappings = {"firstWord", "lastWord", "count"};
		return super.executeQuery(BIGRAMS_FOR_LEMMAS, params, columns, columnMappings, CountBigram.class);
	}
	
	/**
	 * Gets a list of bigrams containing the first word but not the second word
	 * 
	 * @param word1 The first word of the bigram
	 * @param word2 The excluded second word
	 * @return A list of bigrams with counts
	 */
	public List<CountBigram> getBigramForWord1(String word1, String word2) {
		String[] params = {word1, word2};
		String[] columns = {"word1", "word2", "count"};
		String[] columnMappings = {"firstWord", "lastWord", "count"};
		return super.executeQuery(BIGRAMS_FOR_WORD1, params, columns, columnMappings, CountBigram.class);
	}
	
	/**
	 * Returns a list of bigrams containing the second word but not the first word
	 * 
	 * @param word1 The excluded first word
	 * @param word2 The second word of the bigram
	 * @return A list of bigrams with counts
	 */
	public List<CountBigram> getBigramForWord2(String word1, String word2) {
		String[] params = {word1, word2};
		String[] columns = {"word1", "word2", "count"};
		String[] columnMappings = {"firstWord", "lastWord", "count"};
		return super.executeQuery(BIGRAMS_FOR_WORD2, params, columns, columnMappings, CountBigram.class);
	}
	
	/**
	 * Returns the total number of bigrams containing the first word
	 * 
	 * @param word1 The word to check
	 * @return A count of all the bigrams containing that word
	 */
	public int getBigramTotalForWord1(String word1) {
		String[] params = {word1};
		String[] columns = {"c"};
		return super.executeCountQuery(COUNT_FIRST_BIGRAM, params, columns);
	}
	
	/**
	 * Returns the total number of bigrams containing the input word at position 2
	 * 
	 * @param word2 The word in the second position
	 * @return A count of the total number of bigrams in that position
	 */
	public int getBigramTotalForWord2(String word2) {
		String[] params = {word2};
		String[] columns = {"c"};
		return super.executeCountQuery(COUNT_LAST_BIGRAM, params, columns);
	}
}
