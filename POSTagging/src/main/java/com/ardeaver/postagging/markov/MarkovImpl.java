package com.ardeaver.postagging.markov;

import java.util.List;

import com.ardeaver.postagging.dao.PosDao;
import com.ardeaver.postagging.emission.EmissionCalculator;
import com.ardeaver.postagging.transition.TransitionTable;

public class MarkovImpl {
	private static final TransitionTable TRANSITION_TABLE = new TransitionTable();
	private static final List<String> PARTS_OF_SPEECH;
	
	static {
		PosDao posDao = new PosDao();
		PARTS_OF_SPEECH = posDao.getPartsOfSpeech();
	}
	
	private EmissionCalculator emissionCalc;
	
	public MarkovImpl() {
		emissionCalc = new EmissionCalculator();
	}
	
	public String tagSentence(String input) {
		String[] tokens = input.toLowerCase().split("\\s+");
		int[] posBackPointers = runViterbi(tokens);
		
		return constructOutput(posBackPointers);
	}
	
	private int[] runViterbi(String[] tokens) {
		double[][] probs = new double[PARTS_OF_SPEECH.size()][tokens.length];
		int[] backPointers = new int[tokens.length];
		
		String transitionState;
		double viterbiBack, transitionProbability, emissionProbability;
		int p = 0;
		
		for(int i = 0; i < tokens.length; i++) {
			transitionState = i > 0 ? PARTS_OF_SPEECH.get(backPointers[i-1]) : "<s>";
			p = i > 0 ? backPointers[i-1] : 0;
			for(int j = 0; j < PARTS_OF_SPEECH.size(); j++) {
				viterbiBack = i > 0 ? probs[p][i-1] : 1;
				
				transitionProbability = TRANSITION_TABLE.getTransitionProability(transitionState, PARTS_OF_SPEECH.get(j));
				
				emissionProbability = emissionCalc.getEmissionProbability(tokens[i], PARTS_OF_SPEECH.get(j));
				
				probs[j][i] = viterbiBack * transitionProbability * emissionProbability;
				
				int highestPointerIndex = backPointers[i];
				if(probs[j][i] > probs[highestPointerIndex][i]) {
					backPointers[i] = j;
				}
			}
		}
		
		return backPointers;
	}
	
	private String constructOutput(int[] backPointers) {
		String[] pos = new String[backPointers.length];
		
		for(int i = backPointers.length-1; i >= 0; i--) {
			pos[i] = PARTS_OF_SPEECH.get(backPointers[i]);
		}
		
		return String.join(" ", pos);
	}
}
