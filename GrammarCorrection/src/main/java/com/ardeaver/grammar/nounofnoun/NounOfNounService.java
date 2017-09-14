package com.ardeaver.grammar.nounofnoun;

import java.util.ArrayList;
import java.util.List;

import com.ardeaver.grammar.correction.Correction;
import com.ardeaver.grammar.correction.CorrectionService;
import com.ardeaver.grammar.fsa.Transduction;
import com.ardeaver.grammar.ngrams.Bigram;
import com.ardeaver.grammar.ngrams.Trigram;

public class NounOfNounService extends CorrectionService {
	private NounOfNounValidator validator;

	public NounOfNounService() {
		super(new NounOfNounFiniteStateMachine());
		validator = new NounOfNounValidator();
	}

	@Override
	public List<Correction> transformTransductions(
			List<Transduction> transductions) {
		
		List<Correction> corrections = new ArrayList<Correction>();
		
		NounOfNounTransduction nounOfNoun = null;
		Bigram correctionBigram = null;
		Correction correction = null;
		
		for(Transduction transduction : transductions) {
			nounOfNoun = (NounOfNounTransduction) transduction;
			
			correctionBigram = validator.getCorrectionBigram(nounOfNoun);
			correction = createCorrection(correctionBigram, nounOfNoun);
			
			corrections.add(correction);
		}
		
		return corrections;
	}
	
	private Correction createCorrection(Bigram correctionBigram, NounOfNounTransduction transduction) {
		Correction.CorrectionBuilder builder;
		Correction correction;
		int beginIndex = transduction.getBeginIndex();
		int endIndex = transduction.getEndIndex();
		
		if(correctionBigram instanceof Trigram) {
			Trigram correctionTrigram = (Trigram) correctionBigram;
			int headIndex = 0;
			
			if(correctionTrigram.getMiddleWord().trim().equals("'s")) {
				headIndex = 1;
			}
			
			builder = new Correction.CorrectionBuilder(headIndex, beginIndex, endIndex);
			
			if(headIndex == 1) {
				builder.addWord(correctionTrigram.getFirstWord() + correctionTrigram.getMiddleWord());
			} else {
				builder.addWord(correctionTrigram.getFirstWord());
				builder.addWord(correctionTrigram.getMiddleWord());
			}

			builder.addWord(correctionTrigram.getLastWord());
			
		} else {
			builder = new Correction.CorrectionBuilder(1, beginIndex, endIndex);
			
			builder.addWord(correctionBigram.getFirstWord());
			builder.addWord(correctionBigram.getLastWord());
		}
		
		correction = builder.build();
		correction.putToken(transduction.getNouns().get(0));
		correction.putToken(transduction.getNouns().get(1));
		
		return correction;
	}

}
