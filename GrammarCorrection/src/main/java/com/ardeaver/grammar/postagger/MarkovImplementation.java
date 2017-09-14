package com.ardeaver.grammar.postagger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ardeaver.grammar.dao.PartOfSpeechDao;
import com.ardeaver.grammar.dto.CountBigram;
import com.ardeaver.grammar.preprocessing.Token;

public class MarkovImplementation {
	private List<String> partsOfSpeech;
	private PartOfSpeechDao partOfSpeechDao;
	private HashMap<String, Double> emissionMemo, transitionMemo;
	
	public MarkovImplementation() {
		partOfSpeechDao = new PartOfSpeechDao();
		partsOfSpeech = partOfSpeechDao.getPartsOfSpeech();
		
		emissionMemo = new HashMap<String, Double>();
		transitionMemo = new HashMap<String, Double>();
	}
	
	public List<String> getPartsOfSpeech(List<Token> tokens) {
		Viterbi[][] viterbiMatrix = new Viterbi[partsOfSpeech.size()][tokens.size()];
		String transition = "<s>";
		double emissionProbability, transitionProbability, backProbability, viterbiProbability;
		int stop = 1, j;
		boolean emissionFound = false, transitionFound = false;
		String word = "";
		
		List<CountBigram> emissionBigrams;
		
		for(int i = 0; i < tokens.size(); i++) {
			word = tokens.get(i).getHeadWord();
			emissionFound = false;
			transitionFound = false;
			
			emissionBigrams = partOfSpeechDao.getEmission(word);
			
			for(CountBigram eBigram : emissionBigrams) {
				j = partsOfSpeech.indexOf(eBigram.getLastWord());
				emissionProbability = calculateEmissionProbability(eBigram);
				
				if(emissionProbability > 0) {
					emissionFound = true;
				}
				
				stop = i > 0 ? partsOfSpeech.size() : 1;
				if(emissionProbability > 0) {
					for(int k = 0; k < stop; k++) {
						if(i == 0 || viterbiMatrix[k][i-1] != null) {
							transition = i > 0 ? partsOfSpeech.get(k) : "<s>";
							
							transitionProbability = calculateTransitionProbability(transition, partsOfSpeech.get(j));
							backProbability = i > 0 ? viterbiMatrix[k][i-1].getProbability() : 1;
							
							if(viterbiMatrix[j][i] == null) {
								viterbiMatrix[j][i] = new Viterbi();
							}
							
							viterbiProbability = transitionProbability * backProbability * emissionProbability;
							
							viterbiMatrix[j][i].insertViterbi(k, viterbiProbability);
							
							if(transitionProbability > 0) {
								transitionFound = true;
							}
						}
					}
				}
			}
			
			if(!emissionFound) {
				viterbiMatrix = getTransitionBackoff(viterbiMatrix, i-1);
				transitionFound = true;
			}
			
			if(!transitionFound) {
				viterbiMatrix = getEmissionBackoff(viterbiMatrix, word, i);
			}
		}
		
		int[] indices = getIndices(viterbiMatrix, tokens.size());
		return getPartsOfSpeech(partsOfSpeech, indices);
	}
	
	private int[] getIndices(Viterbi[][] viterbiMatrix, int maxLength) {
		int[] indices = new int[maxLength];
		
		int index = getHighestViterbiEntry(viterbiMatrix, maxLength-1);
		
		for(int j = maxLength-1; j >= 0; j--) {
			indices[j] = index;
			index = viterbiMatrix[index][j].getBackIndex();
		}
		
		return indices;
	}
	
	private List<String> getPartsOfSpeech(List<String> partsOfSpeech, int[] indices) {
		List<String> tags = new ArrayList<String>();
		
		for(int i = 0; i < indices.length; i++) {
			tags.add(partsOfSpeech.get(indices[i]));
		}
		
		return tags;
	}
	
	private double calculateEmissionProbability(CountBigram emissionBigram) {
		Double emissionProbability = getEmissionMemoEntry(emissionBigram.getFirstWord(), emissionBigram.getLastWord());
		
		if(emissionProbability != null) {
			return emissionProbability.doubleValue();
		}
		
		int totalCount = partOfSpeechDao.getTotalForEmission(emissionBigram.getFirstWord());
		
		if(totalCount == 0) {
			return 0;
		}
		
		double probability = ((double) emissionBigram.getCount())/totalCount;
		putEmissionProbability(emissionBigram.getFirstWord(), emissionBigram.getLastWord(), probability);
		return probability;
	}
	
	private double calculateTransitionProbability(String pos1, String pos2) {
		Double transitionProbability = getTransitionMemoEntry(pos1, pos2);
		
		if(transitionProbability != null) {
			return transitionProbability.doubleValue();
		}
		
		CountBigram transition = partOfSpeechDao.getBigrams(pos1, pos2);
		int totalCount = partOfSpeechDao.getTotalForBigram(pos1);
		
		if(totalCount == 0 || transition == null) {
			return 0;
		}
		
		double probability = ((double) transition.getCount())/totalCount;
		putTransitionProbability(pos1, pos2, probability);
		return probability;
	}
	
	private Viterbi[][] getTransitionBackoff(Viterbi[][] viterbiMatrix, int lastIndex) {
		if(lastIndex >= 0) {
			for(int i = 0; i < viterbiMatrix.length; i++) {
				if(viterbiMatrix[i][lastIndex] != null) {
					CountBigram bigram = partOfSpeechDao.getMostCommonTransition(partsOfSpeech.get(i));
					
					if(bigram != null) {
						double transitionProbability = calculateTransitionProbability(bigram.getFirstWord(), bigram.getLastWord());
						
						if(viterbiMatrix[partsOfSpeech.indexOf(bigram.getLastWord())][lastIndex+1] == null) {
							viterbiMatrix[partsOfSpeech.indexOf(bigram.getLastWord())][lastIndex+1] = new Viterbi();
						}
						
						double probability = transitionProbability * viterbiMatrix[i][lastIndex].getProbability();
						
						viterbiMatrix[partsOfSpeech.indexOf(bigram.getLastWord())][lastIndex+1].insertViterbi(i, probability);
					}
				}
			}
		} else {
			CountBigram bigram = partOfSpeechDao.getMostCommonTransition("<s>");
			
			if(bigram != null) {
				double transitionProbability = calculateTransitionProbability("<s>", bigram.getLastWord());
				
				viterbiMatrix[partsOfSpeech.indexOf(bigram.getLastWord())][lastIndex+1] = new Viterbi();
				viterbiMatrix[partsOfSpeech.indexOf(bigram.getLastWord())][lastIndex+1].insertViterbi(0, transitionProbability);
			}
		}
		return viterbiMatrix;
	}
	
	private Viterbi[][] getEmissionBackoff(Viterbi[][] viterbiMatrix, String word, int currentIndex) {
		CountBigram emission = partOfSpeechDao.getMostCommonEmission(word);
		
		if(emission != null) {
			double probability = calculateEmissionProbability(emission);
			int backIndex = 0;
			
			if(currentIndex > 0) {
				backIndex = getHighestViterbiEntry(viterbiMatrix, currentIndex-1);
				probability *= viterbiMatrix[backIndex][currentIndex-1] != null ? viterbiMatrix[backIndex][currentIndex-1].getProbability() : 0;
			}
			
			viterbiMatrix[partsOfSpeech.indexOf(emission.getLastWord())][currentIndex] = new Viterbi();
			viterbiMatrix[partsOfSpeech.indexOf(emission.getLastWord())][currentIndex].insertViterbi(backIndex, probability);
		}
		
		return viterbiMatrix;
	}
	
	private int getHighestViterbiEntry(Viterbi[][] viterbiMatrix, int index) {
		double highestProbability = 0;
		int highestIndex = 0;
		
		for(int i = 0; i < viterbiMatrix.length; i++) {
			if(viterbiMatrix[i][index] != null && viterbiMatrix[i][index].getProbability() > highestProbability) {
				highestProbability = viterbiMatrix[i][index].getProbability();
				highestIndex = i;
			}
		}
		
		return highestIndex;
	}
	
	private Double getEmissionMemoEntry(String word, String pos) {
		String key = word + " " + pos;
		return emissionMemo.get(key);
	}
	
	private void putEmissionProbability(String word, String pos, double probability) {
		String key = word + " " + pos;
		emissionMemo.put(key, new Double(probability));
	}
	
	private Double getTransitionMemoEntry(String pos1, String pos2) {
		String key = pos1 + " " + pos2;
		return transitionMemo.get(key);
	}
	
	private void putTransitionProbability(String pos1, String pos2, double probability) {
		String key = pos1 + " " + pos2;
		transitionMemo.put(key, new Double(probability));
	}
}
