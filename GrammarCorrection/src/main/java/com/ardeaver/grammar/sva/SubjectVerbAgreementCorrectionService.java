package com.ardeaver.grammar.sva;

import java.util.ArrayList;
import java.util.List;

import com.ardeaver.grammar.correction.Correction;
import com.ardeaver.grammar.correction.CorrectionService;
import com.ardeaver.grammar.fsa.Transduction;
import com.ardeaver.grammar.preprocessing.Token;

/**
 * The correction service for subject verb agreement
 * 
 * @author Andrew Deaver
 * @version 1.0
 * @since 2017-02-28
 */
public class SubjectVerbAgreementCorrectionService extends CorrectionService {
	
	// ***** INSTANCE VARIABLES *****
	private SubjectVerbAgreementInputConverter inputConverter;
	private SubjectVerbAgreementHelper helper;
	// ***** END INSTANCE VARIABLES *****

	public SubjectVerbAgreementCorrectionService() {
		super(new SubjectVerbAgreementFiniteStateMachine());
		
		inputConverter = new SubjectVerbAgreementInputConverter();
		helper = new SubjectVerbAgreementHelper();
	}

	@Override
	protected List<Correction> transformTransductions(
			List<Transduction> transductions) {
		
		List<Correction> finalCorrections = new ArrayList<Correction>();
		
		SubjectVerbAgreementTransduction svaTransduction;
		List<SubjectVerbAgreementPair> pairs;
		List<SubjectVerbAgreementCorrection> corrections;
		
		for(Transduction transduction : transductions) {
			if(transduction instanceof SubjectVerbAgreementTransduction) {
				svaTransduction = (SubjectVerbAgreementTransduction) transduction;
				
				// Existential There does not work like nouns
				if(!svaTransduction.getHeadNoun().getPartOfSpeech().equals("EX")) {
					pairs = getPairs(svaTransduction);
					corrections = getVerbCorrections(pairs);
				
					finalCorrections.addAll(generateFinalCorrectionsForTransduction(corrections, svaTransduction));
				}
			}
		}
		
		return finalCorrections;
	}
	
	/**
	 * This method gets all the pairs of words that should be corrected together. For instance,
	 * this would be the first verb and the head noun, the second verb and the first verb, etc.
	 * 
	 * @param transduction The SVA transduction
	 * @return A list of all the pairs that affect each other
	 */
	private List<SubjectVerbAgreementPair> getPairs(SubjectVerbAgreementTransduction transduction) {
		List<SubjectVerbAgreementPair> agreementPairs = new ArrayList<SubjectVerbAgreementPair>();
		
		SubjectVerbAgreementPair currentPair = null;
		List<Token> verbsAdverbs = transduction.getVerbsAdverbs();
		
		int inputType;
		
		for(int i = 0; i < verbsAdverbs.size(); i++) {
			inputType = inputConverter.convertInputToType(verbsAdverbs.get(i));
			
			if(inputType == SubjectVerbAgreementInputConverter.VERB || 
				inputType == SubjectVerbAgreementInputConverter.AUX ||
				inputType == SubjectVerbAgreementInputConverter.GERUND) {
				
				if(currentPair == null) {
					currentPair = new SubjectVerbAgreementPair(transduction.getHeadNoun(), -1, verbsAdverbs.get(i), i);
				} else {
					currentPair = new SubjectVerbAgreementPair(currentPair.getToken2(), currentPair.getIndex2(), verbsAdverbs.get(i), i);
				}
				
				agreementPairs.add(currentPair);
			}
		}
		
		return agreementPairs;
	}
	
	/**
	 * This function uses the pairs of tokens to replace the second token with its valid word
	 * 
	 * @param pairs The pairs of tokens
	 * @return A list of the correct words
	 */
	private List<SubjectVerbAgreementCorrection> getVerbCorrections(List<SubjectVerbAgreementPair> pairs) {
		List<SubjectVerbAgreementCorrection> verbCorrections = new ArrayList<SubjectVerbAgreementCorrection>();
		
		for(SubjectVerbAgreementPair pair : pairs) {
			String word = helper.getValidVerb(pair.getToken1(), pair.getToken2());
			verbCorrections.add(new SubjectVerbAgreementCorrection(word, pair.getIndex2()));
		}
		
		return verbCorrections;
	}
	
	/**
	 * This method converts the corrections that are generated by the DAO into
	 * corrections that can be used by the correction service
	 * 
	 * @param corrections The output of the DAO
	 * @param transduction The transduction that generated the potential errors
	 * @return Corrections in the format that can be used by the correction service
	 */
	private List<Correction> generateFinalCorrectionsForTransduction(List<SubjectVerbAgreementCorrection> corrections, SubjectVerbAgreementTransduction transduction) {
		List<Correction> correctionsForTransduction = new ArrayList<Correction>();
		
		int correctionIndex = 0, startIndex = 0;
		
		if(transduction.getType() == SubjectVerbAgreementTransductionType.AUXILIARY) {
			Correction.CorrectionBuilder builder = new Correction.CorrectionBuilder(0, transduction.getBeginIndex(), transduction.getBeginIndex());
			
			builder.addWord(corrections.get(0).getWord());
			
			Correction correction = builder.build();
			correction.putToken(transduction.getVerbsAdverbs().get(0));
			
			correctionsForTransduction.add(correction);
			correctionIndex = 1;
			startIndex = 1;
		}
		
		Correction.CorrectionBuilder finalBuilder = new Correction.CorrectionBuilder(0, transduction.getEndIndex(), transduction.getEndIndex()+(transduction.getVerbsAdverbs().size()-(correctionIndex+1)));
		
		for(int i = startIndex; i < transduction.getVerbsAdverbs().size(); i++) {
			if(correctionIndex < corrections.size() && i == corrections.get(correctionIndex).getIndex()) {
				finalBuilder.addWord(corrections.get(correctionIndex).getWord());
				correctionIndex++;
			} else {
				finalBuilder.addWord(transduction.getVerbsAdverbs().get(i).getHeadWord());
			}
		}
		
		Correction finalCorrection = finalBuilder.build();
		
		for(int i = startIndex; i < transduction.getVerbsAdverbs().size(); i++) {
			finalCorrection.putToken(transduction.getVerbsAdverbs().get(i), i-startIndex);
		}
		
		correctionsForTransduction.add(finalCorrection);
		
		return correctionsForTransduction;
	}
}
