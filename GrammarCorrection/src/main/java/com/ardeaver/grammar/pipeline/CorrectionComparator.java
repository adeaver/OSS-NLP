package com.ardeaver.grammar.pipeline;

import java.util.Comparator;

public class CorrectionComparator implements Comparator<Correction> {

	public int compare(Correction arg0, Correction arg1) {
		if(arg0.getSentence().toLowerCase().equals(arg1.getSentence().toLowerCase())) {
			return 0;
		}
		
		if(arg0.getProbability() > arg1.getProbability()) {
			return -1;
		} else {
			return 1;
		}
	}

}
