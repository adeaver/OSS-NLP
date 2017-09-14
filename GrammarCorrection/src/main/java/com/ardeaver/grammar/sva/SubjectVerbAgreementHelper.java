package com.ardeaver.grammar.sva;

import java.util.ArrayList;
import java.util.List;

import com.ardeaver.grammar.dao.SubjectVerbAgreementDao;
import com.ardeaver.grammar.dao.WordFormDao;
import com.ardeaver.grammar.dto.SubjectVerbAgreementDto;
import com.ardeaver.grammar.preprocessing.Token;

/**
 * This is a helper class for the SVA module. It helps with database
 * access. It is equivalent to a service
 * 
 * @author Andrew Deaver
 * @version 1.0
 * @since 2017-02-28
 */
public class SubjectVerbAgreementHelper {
	
	// ***** INSTANCE VARIABLES *****
	private SubjectVerbAgreementDao svaDao;
	private WordFormDao wordFormDao;
	// ***** END INSTANCE VARIABLES *****
	
	/**
	 * Constructor
	 */
	public SubjectVerbAgreementHelper() {
		svaDao = new SubjectVerbAgreementDao();
		wordFormDao = new WordFormDao();
	}
	
	/**
	 * This method determines the valid form of the second token in this
	 * correction
	 * 
	 * @param token1 The controlling token, this will not be modified
	 * @param token2 The receiving token
	 * @return The suggestion for a replacement of the second token
	 */
	public String getValidVerb(Token token1, Token token2) {
		String word2posBase = token2.getPartOfSpeech().substring(0, 2) + "%";
		
		List<SubjectVerbAgreementDto> dtos;
		
		if(token1.getPartOfSpeech().startsWith("PRP")) {
			dtos = svaDao.getFormsBasedOnWord(token1.getHeadWord(), word2posBase);
		} else {
			dtos = svaDao.getForms(token1.getPartOfSpeech(), word2posBase);
		}
		
		dtos = filterBelowAverage(dtos);
		
		if(dtos.size() > 0 && !isValidVerbForm(token2.getPartOfSpeech(), dtos)) {
			String finalWord = "";
			
			List<SubjectVerbAgreementDto> newDtos = svaDao.getPresentTenseForms(token1.getPartOfSpeech(), word2posBase);
			String pos = "";
			
			if(newDtos.size() > 0 && 
					(token1.getPartOfSpeech().startsWith("NN") || token1.getPartOfSpeech().equals("PRP") ||
							token1.getPartOfSpeech().startsWith("BE"))) {
				pos = newDtos.get(0).getPartOfSpeech();
			} else {
				pos = dtos.get(0).getPartOfSpeech();
			}
			
			finalWord = wordFormDao.getWordForPOS(token2.getHeadWord(), word2posBase, pos);
			
			if(token1.getHeadWord().toUpperCase().equals("I")) {
				if(newDtos.get(0).getPartOfSpeech().contains("BEZ")) {
					// surprisingly, this is the only rule I needed to hard code.
					return "am";
				}
			}
			
			if(finalWord != null && !finalWord.equals("")) {
				return finalWord;
			}
		}
		
		return token2.getHeadWord();
	}
	
	/**
	 * This method filters out any below average occurrences of subject verb types
	 * This is necessary to get rid of rare occurrences
	 * 
	 * @param dtos The results returned by the DAO class
	 * @return The dtos with above average occurrences
	 */
	private List<SubjectVerbAgreementDto> filterBelowAverage(List<SubjectVerbAgreementDto> dtos) {
		
		List<SubjectVerbAgreementDto> output = new ArrayList<SubjectVerbAgreementDto>();
		
		if(dtos.size() > 0) {
			int total = 0;
			double average = 0;
			
			for(SubjectVerbAgreementDto dto : dtos) {
				total += dto.getCount();
			}
			
			average = total / dtos.size();
			
			for(SubjectVerbAgreementDto dto : dtos) {
				if(dto.getCount() >= average) {
					output.add(dto);
				}
			}
		}
		
		return output;
	}
	
	/**
	 * This method is used to determine if the current part of speech occurs
	 * 
	 * @param partOfSpeech The current part of speech
	 * @param dtos The DTO objects with above average occurrences
	 * @return Whether or not this combination is valid
	 */
	private boolean isValidVerbForm(String partOfSpeech, List<SubjectVerbAgreementDto> dtos) {
		for(SubjectVerbAgreementDto dto : dtos) {
			if(dto.getPartOfSpeech().equals(partOfSpeech)) {
				return true;
			}
		}
		
		return false;
	}
}
