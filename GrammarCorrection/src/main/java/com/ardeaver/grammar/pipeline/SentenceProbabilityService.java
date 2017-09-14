package com.ardeaver.grammar.pipeline;

import java.util.List;

import com.ardeaver.grammar.dao.BigramsDao;
import com.ardeaver.grammar.dto.CountBigram;

public class SentenceProbabilityService {
	private BigramsDao dao;
	
	public SentenceProbabilityService() {
		dao = new BigramsDao();
	}
	
	public double getSentenceProbability(String sentence) {
		String[] tokens = sentence.split("\\s+");
		double probability = 1;
		
		for(int i = 0; i < tokens.length-2; i++) {
			probability *= getTwoWordProbability(tokens[i], tokens[i+1]);
		}
		
		return probability;
	}
	
	private double getTwoWordProbability(String word1, String word2) {
		int total = dao.getBigramTotalForWord1(word1);
		List<CountBigram> bigrams = dao.getBigramForWords(word1, word2);
		
		if(bigrams.size() > 0 && total > 0) {
			return ((double) bigrams.get(0).getCount()) / ((double) total);
		} else {
			return 0;
		}
	}
}
