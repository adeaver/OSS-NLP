package com.ardeaver.grammar.determiner;

import java.util.List;

import com.ardeaver.grammar.correction.Correction;
import com.ardeaver.grammar.correction.CorrectionType;
import com.ardeaver.grammar.dao.DeterminerDao;
import com.ardeaver.grammar.dto.DeterminerDto;
import com.ardeaver.grammar.dto.HasDeterminerDto;

public class DeterminerHelper {
	private DeterminerDao determinerDao;
	
	public DeterminerHelper() {
		determinerDao = new DeterminerDao();
	}
	
	public Correction getCorrection(DeterminerTransduction determinerTransduction) {
		
		if(determinerTransduction.getStart() == null || !determinerTransduction.getStart().getPartOfSpeech().equals("PDT")) {
			// Lexical transformations CANNOT handle predeterminers
			
			String headNoun = determinerTransduction.getNoun().getHeadWord();
			
			if(determinerTransduction.getDeterminer() == null) {
				List<DeterminerDto> noDeterminer = determinerDao.getNoDeterminers(headNoun);
				
				if(noDeterminer.isEmpty()) {
					return findInsertionDeterminer(determinerTransduction);
				}
			} else {
				String determiner = determinerTransduction.getDeterminer().getHeadWord();
				
				List<HasDeterminerDto> determiners = determinerDao.getDeterminers(headNoun);
				List<DeterminerDto> noDeterminer = determinerDao.getNoDeterminers(headNoun);
				
				if(!determiners.contains(determiner.toLowerCase())) {
					if(!determiners.isEmpty()) {
						return findReplacementDeterminer(determinerTransduction);
					} else {
						if(!noDeterminer.isEmpty()) {
							int startIndex = determinerTransduction.getStart() != null ? determinerTransduction.getBeginIndex() + 1 : determinerTransduction.getBeginIndex();
							Correction.CorrectionBuilder correctionBuilder = new Correction.CorrectionBuilder(0, startIndex, startIndex);
							correctionBuilder.setCorrectionType(CorrectionType.DELETION);
							return correctionBuilder.build();
						}
					}
				}
			}
		}
		
		return null;
	}
	
	private Correction findReplacementDeterminer(DeterminerTransduction determinerTransduction) {
		String noun = determinerTransduction.getNoun().getHeadWord();
		String mostLikelyDeterminer = findMostLikelyDeterminer(noun);
		
		if(mostLikelyDeterminer != null) {
			int startIndex = determinerTransduction.getBeginIndex();
			
			if(determinerTransduction.getStart() != null) {
				startIndex++;
			}
			
			Correction.CorrectionBuilder correctionBuilder = new Correction.CorrectionBuilder(0, startIndex, startIndex);
			correctionBuilder.addWord(mostLikelyDeterminer);
			correctionBuilder.setCorrectionType(CorrectionType.REPLACEMENT);
			
			return correctionBuilder.build();
		}
		
		return null;
	}
	
	private Correction findInsertionDeterminer(DeterminerTransduction determinerTransduction) {
		String noun = determinerTransduction.getNoun().getHeadWord();
		String mostLikelyDeterminer = findMostLikelyDeterminer(noun);
		
		if(mostLikelyDeterminer != null) {
			int startIndex = determinerTransduction.getStart() == null ? determinerTransduction.getBeginIndex()-1 : determinerTransduction.getBeginIndex()+1;
			Correction.CorrectionBuilder correctionBuilder = new Correction.CorrectionBuilder(0, startIndex, startIndex);
			correctionBuilder.addWord(mostLikelyDeterminer);
			correctionBuilder.setCorrectionType(CorrectionType.INSERTION);
			
			return correctionBuilder.build();
		}
		
		return null;
	}
	
	private String findMostLikelyDeterminer(String noun) {
		List<HasDeterminerDto> determiners = determinerDao.getDeterminers(noun);
		return !determiners.isEmpty() ? determiners.get(0).getDeterminer() : null;
	}
}
