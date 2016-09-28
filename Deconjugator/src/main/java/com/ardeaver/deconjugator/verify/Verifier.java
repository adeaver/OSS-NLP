package com.ardeaver.deconjugator.verify;

import com.ardeaver.deconjugator.dao.TokenDao;
import com.ardeaver.deconjugator.util.DeconstructedVerb;

public class Verifier {
	/* This will probably have to be modified to take in a list of Deconstructed Verbs later */
	public static String returnMostLikely(DeconstructedVerb verb) {
		TokenDao tokenDao = new TokenDao();
		String[] possibleVerbs = verb.reconstructVerb();
		int highestCount = 0, getCount = 0;
		String mostLikely = "";
		
		for(String pVerb : possibleVerbs) {
			getCount = tokenDao.getCountForWord(pVerb);
			if(highestCount < getCount) {
				highestCount = getCount;
				mostLikely = pVerb;
			}
		}
		
		return mostLikely;
		
	}
}
