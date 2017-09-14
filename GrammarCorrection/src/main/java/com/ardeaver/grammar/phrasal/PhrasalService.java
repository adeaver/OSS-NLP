package com.ardeaver.grammar.phrasal;

import java.util.ArrayList;
import java.util.List;

import com.ardeaver.grammar.correction.Correction;
import com.ardeaver.grammar.dao.PhrasalDao;
import com.ardeaver.grammar.dto.CountSingleNounPhrasal;
import com.ardeaver.grammar.dto.CountTwoNounPhrasal;

public class PhrasalService {
	private PhrasalDao phrasalDao;
	
	public PhrasalService() {
		phrasalDao = new PhrasalDao();
	}
	
	public Correction createCorrection(PhrasalVerbTransduction t) {
		if(t.getType() == PhrasalVerbTransduction.PREPOSITION_TWO_NOUNS) {
			return createPrepositionalTwoNounCorrection(t);
		}
		
		if(t.getType() == PhrasalVerbTransduction.PARTICLE_ONE_NOUN) {
			return createParticleOneNounCorrection(t);
		}
		
		return null;
	}
	
	private CountTwoNounPhrasal getMaxTwoNounPhrasal(List<CountTwoNounPhrasal> phrasals) {
		int maxCount = 0;
		CountTwoNounPhrasal maxVal = null;
		
		for(CountTwoNounPhrasal b : phrasals) {
			if(b.getCount() > maxCount) {
				maxCount = b.getCount();
				maxVal = b;
			}
		}
		
		return maxVal;
	}
	
	private List<CountTwoNounPhrasal> generateTwoNounBackoffs(String verb, String noun, String backoff) {
		List<CountTwoNounPhrasal> backoffs = new ArrayList<CountTwoNounPhrasal>();
		List<CountTwoNounPhrasal> frontBackoffs = phrasalDao.getPhrasalNoun1Backoff(verb, noun);
		List<CountTwoNounPhrasal> backBackoffs = phrasalDao.getPhrasalNoun2BackOff(verb, noun);
		
		for(CountTwoNounPhrasal front : frontBackoffs) {
			front.setNoun2(backoff);
			backoffs.add(front);
		}
		
		for(CountTwoNounPhrasal back : backBackoffs) {
			back.setNoun1(backoff);
			backoffs.add(back);
		}
		
		return backoffs;
	}
	
	private Correction createPrepositionalTwoNounCorrection(PhrasalVerbTransduction t) {
		String verb = t.getMainVerb().getHeadWord();
		String noun1 = t.getNouns().get(0).getHeadWord();
		String noun2 = t.getNouns().get(1).getHeadWord();
		
		List<CountTwoNounPhrasal> twoNounCorrections = phrasalDao.getPhrasalTwoNoun(verb, noun1, noun2);
		
		if(twoNounCorrections.isEmpty()) {
			twoNounCorrections.addAll(generateTwoNounBackoffs(verb, noun1, noun2));
			twoNounCorrections.addAll(generateTwoNounBackoffs(verb, noun2, noun1));
		}
		
		CountTwoNounPhrasal max = getMaxTwoNounPhrasal(twoNounCorrections);
		
		if(max != null) {
			Correction.CorrectionBuilder builder = new Correction.CorrectionBuilder(0, t.getBeginIndex(), t.getEndIndex());
			builder.addWord(max.getVerb());
			builder.addWord(max.getNoun1());
			builder.addWord(max.getPrep());
			builder.addWord(max.getNoun2());	
			
			Correction correction = builder.build();
			correction.putToken(t.getNouns().get(0));
			correction.putToken(t.getNouns().get(1));
			return correction;
		}
		
		return null;
	}
	
	private Correction createParticleOneNounCorrection(PhrasalVerbTransduction t) {
		String verb = t.getMainVerb().getHeadWord();
		String noun = t.getNouns().get(0).getHeadWord();
		
		List<CountSingleNounPhrasal> mids = phrasalDao.getParticleMid(verb, noun);
		List<CountSingleNounPhrasal> ends = phrasalDao.getParticleEnd(verb, noun);
		
//		if(mids.isEmpty() && ends.isEmpty()) {
//			Use for backoff	
//		}
		
		CountSingleNounPhrasal midMax = getMaxSingleNoun(mids);
		CountSingleNounPhrasal endMax = getMaxSingleNoun(ends);
		
		if(!(midMax == null && endMax == null)) {
			Correction.CorrectionBuilder builder = new Correction.CorrectionBuilder(0, t.getBeginIndex(), t.getEndIndex());
			
			if(midMax != null && (endMax == null || midMax.getCount() >= endMax.getCount())) {
				builder.addWord(t.getMainVerb().getHeadWord());
				builder.addWord(t.getNouns().get(0).getHeadWord());
				builder.addWord(midMax.getPrep());
			}
			
			if(endMax != null && (midMax == null || endMax.getCount() > midMax.getCount())) {
				builder.addWord(t.getMainVerb().getHeadWord());
				builder.addWord(endMax.getPrep());
				builder.addWord(t.getNouns().get(0).getHeadWord());
			}
			
			Correction correction = builder.build();
			correction.putToken(t.getNouns().get(0));
			
			return correction;
		}
		
		return null;
 	}
	
	private CountSingleNounPhrasal getMaxSingleNoun(List<CountSingleNounPhrasal> phrasals) {
		int maxCount = 0;
		CountSingleNounPhrasal maxVal = null;
		
		for(CountSingleNounPhrasal b : phrasals) {
			if(b.getCount() > maxCount) {
				maxCount = b.getCount();
				maxVal = b;
			}
		}
		
		return maxVal;
	}
}
