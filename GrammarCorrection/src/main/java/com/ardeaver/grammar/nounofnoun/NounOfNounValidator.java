package com.ardeaver.grammar.nounofnoun;

import java.util.List;

import com.ardeaver.grammar.dao.BigramsDao;
import com.ardeaver.grammar.dao.TrigramsDao;
import com.ardeaver.grammar.dto.CountBigram;
import com.ardeaver.grammar.dto.CountTrigram;
import com.ardeaver.grammar.ngrams.Bigram;
import com.ardeaver.grammar.ngrams.Trigram;

public class NounOfNounValidator {
	private BigramsDao bigramsDao;
	private TrigramsDao trigramsDao;
	
	public NounOfNounValidator() {
		bigramsDao = new BigramsDao();
		trigramsDao = new TrigramsDao();
	}
	
	public Bigram getCorrectionBigram(NounOfNounTransduction transduction) {
		String word1 = transduction.getNouns().get(0).getHeadWord();
		String word2 = transduction.getNouns().get(1).getHeadWord();
		
		List<CountBigram> bigrams = getBigramSolutions(word1, word2);
		List<CountTrigram> trigrams = getTrigramSolutions(word1, word2);
		
		Bigram correction = getMostOften(bigrams, trigrams);
		
		if(correction == null) {
			return new Trigram(word1, "of", word2);
		}
		
		return correction;
	}
	
	private Bigram getMostOften(List<CountBigram> bigrams, List<CountTrigram> trigrams) {
		Bigram solution = null;
		int count = 0;
		
		for(CountBigram bigram : bigrams) {
			if(bigram.getCount() > count) {
				count = bigram.getCount();
				solution = bigram;
			}
		}
		
		for(CountTrigram trigram : trigrams) {
			if(trigram.getCount() > count) {
				count = trigram.getCount();
				solution = trigram;
			}
		}
		
		return solution;
	}
	
	private List<CountBigram> getBigramSolutions(String word1, String word2) {
		return bigramsDao.getBigramForWords(word2, word1);
	}
	
	private List<CountTrigram> getTrigramSolutions(String word1, String word2) {
		List<CountTrigram> solutions = trigramsDao.getTrigram(word1, "of", word2);
		solutions.addAll(trigramsDao.getTrigram(word2, "'s", word1));
		
		return solutions;
	}
}
