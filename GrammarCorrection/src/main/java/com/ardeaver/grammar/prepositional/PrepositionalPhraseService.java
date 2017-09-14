package com.ardeaver.grammar.prepositional;

import java.util.ArrayList;
import java.util.List;

import com.ardeaver.grammar.correction.Correction;
import com.ardeaver.grammar.correction.CorrectionService;
import com.ardeaver.grammar.dao.BigramsDao;
import com.ardeaver.grammar.dao.PrepositionalTranslationDao;
import com.ardeaver.grammar.dao.WordFormDao;
import com.ardeaver.grammar.dto.CountBigram;
import com.ardeaver.grammar.dto.PrepositionalPhrase;
import com.ardeaver.grammar.fsa.Transduction;
import com.ardeaver.grammar.preprocessing.Token;

/**
 * This is the implementation of CorrectionService for Prepositonal phrases.
 * Each submodule reimplements this. Providing its own FiniteStateMachine.
 * 
 * @author Andrew Deaver
 * @version 1.0
 * @since 2017-02-08
 */
public abstract class PrepositionalPhraseService extends CorrectionService {
	
	// ***** INSTANCE VARIABLES *****
	private PrepositionalTranslationDao translationDao;
	private WordFormDao wordFormDao;
	private BigramsDao bigramsDao;
	// ***** END INSTANCE VARIABLES *****

	/**
	 * Constructor for Prepositional Phrase correction service. Must be
	 * provided a Finite State Machine by the submodule that implements it.
	 * 
	 * @param fst Finite State Machine of the submodule
	 */
	public PrepositionalPhraseService(PrepositionalPhraseFiniteStateMachine fst, PrepositionalTranslationDao translationDao) {
		super(fst);
		this.translationDao = translationDao;
		wordFormDao = new WordFormDao();
		bigramsDao = new BigramsDao();
	}

	@Override
	public List<Correction> transformTransductions(
			List<Transduction> transductions) {
		List<Correction> corrections = new ArrayList<Correction>();
		PrepositionalPhrase prepPhrase = null;
		PrepositionalPhraseTransduction pt;
		
		for(Transduction transduction : transductions) {
			pt = (PrepositionalPhraseTransduction) transduction;
			
			if(canCorrect(pt)) {
				if(pt.isParseable()) {
					prepPhrase = getPrepositionalPhraseTranslation(transduction);
					
					if(prepPhrase != null) {
						corrections.add(convertToCorrection(prepPhrase, pt));
					}
				}
			}
		}
		
		return corrections;
	}
	
	/**
	 * This method gets each of the translations for the prepositional phrases.
	 * This means that it searches the database for possible prepositions.
	 * 
	 * @param transduction The potential error found by the FST
	 * @return An object containing information pertaining to the correct preposition (null if not found)
	 */
	private PrepositionalPhrase getPrepositionalPhraseTranslation(Transduction transduction) {
		PrepositionalPhraseTransduction prepTransduction;
		PrepositionalPhrase prepPhrase = null;
		String lemma1 = "", lemma2 = "";
		
		prepTransduction = (PrepositionalPhraseTransduction) transduction;
		lemma1 = wordFormDao.getLemma(prepTransduction.getMains().get(0).getHeadWord());
		lemma2 = wordFormDao.getLemma(prepTransduction.getMains().get(1).getHeadWord());
		
		
		if(!lemma1.equals("") && !lemma2.equals("")) {
			prepPhrase = translationDao.getTranslation(lemma1, lemma2);
		}
		
//		if(prepPhrase == null) {
//			List<String> possibleVerbForms = wordFormDao.getLemmaPOS(lemma2);
//			
//			for(String verbForms : possibleVerbForms) {
//				prepPhrase = translationDao.getTranslation(lemma1, verbForms);
//				
//				if(prepPhrase != null) {
//					prepPhrase.setWord2(wordFormDao.getWordForPOS(lemma2, prepPhrase.getWord2()));
//					break;
//				}
//			}
//		}
		
		return prepPhrase;
	}
	
	/**
	 * This method converts the PrepositionalPhrase data transfer object to a correction
	 * object that can be applied by the CorrectionService
	 * 
	 * @param prepPhrase The data transfer object
	 * @param transduction The potential error that generated the transfer object
	 * @return The correction object representing the new prepositional phrase
	 */
	private Correction convertToCorrection(PrepositionalPhrase prepPhrase, PrepositionalPhraseTransduction transduction) {
		String word2 = prepPhrase.getWord2();
		String prep = prepPhrase.getPrep();
		int beginIndex = transduction.getBeginIndex();
		int endIndex = transduction.getEndIndex();
		
		Correction.CorrectionBuilder builder = new Correction.CorrectionBuilder(0, beginIndex, endIndex);
		builder.addWord(transduction.getMains().get(0).getHeadWord());
		builder.addWord(prep);
		
		for(Token t : transduction.getExtras()) {
			builder.addWord(t.getHeadWord());
		}
		
		builder.addWord(word2);
		
		Correction correction = builder.build();
		correction.putToken(transduction.getMains().get(0));
		correction.putToken(transduction.getMains().get(1));
		
		return correction;
	}
	
	/**
	 * This method is used for correcting transductions that don't contain
	 * a preposition. It makes sure that the two words don't appear as a bigram.
	 * 
	 * @param pt The transduction to check
	 * @return Whether or not those words appear as a bigram
	 */
	private boolean canCorrect(PrepositionalPhraseTransduction pt) {
		if(pt.getMains().size() > 1) {
			String word1 = pt.getMains().get(0).getHeadWord();
			String word2 = pt.getMains().get(1).getHeadWord();
			
			List<CountBigram> bigrams = bigramsDao.getBigramForWords(word1, word2);
			
			return bigrams.isEmpty();
		}
		
		return false;
	}
}
