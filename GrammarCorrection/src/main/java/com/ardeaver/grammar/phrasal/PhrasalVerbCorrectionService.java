package com.ardeaver.grammar.phrasal;

import java.util.ArrayList;
import java.util.List;

import com.ardeaver.grammar.correction.Correction;
import com.ardeaver.grammar.correction.CorrectionService;
import com.ardeaver.grammar.fsa.Transduction;

public class PhrasalVerbCorrectionService extends CorrectionService {
	private PhrasalService phrasalService;
	
	public PhrasalVerbCorrectionService() {
		super(new PhrasalVerbFiniteStateMachine());
		phrasalService = new PhrasalService();
	}

	@Override
	protected List<Correction> transformTransductions(
			List<Transduction> transductions) {
		List<Correction> corrections = new ArrayList<Correction>();
		Correction pc;
		PhrasalVerbTransduction pvt;
		
		for(Transduction t : transductions) {
			pvt = (PhrasalVerbTransduction) t;
			pc = phrasalService.createCorrection(pvt);
			
			if(pc != null) {
				corrections.add(pc);
			}
		}
		
		return corrections;
	}
}
