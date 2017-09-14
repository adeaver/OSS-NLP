package com.ardeaver.grammar.determiner;

import java.util.ArrayList;
import java.util.List;

import com.ardeaver.grammar.correction.Correction;
import com.ardeaver.grammar.correction.CorrectionService;
import com.ardeaver.grammar.dao.DeterminerDao;
import com.ardeaver.grammar.dto.IndefiniteDto;
import com.ardeaver.grammar.fsa.Transduction;
import com.ardeaver.grammar.preprocessing.Token;

public class DeterminerService extends CorrectionService {
	private DeterminerHelper determinerHelper;

	public DeterminerService() {
		super(new DeterminerFiniteStateMachine());
		determinerHelper = new DeterminerHelper();
	}

	@Override
	protected List<Correction> transformTransductions(
			List<Transduction> transductions) {
		List<Correction> corrections = new ArrayList<Correction>();
		Correction c;
		DeterminerTransduction dt;
		
		for(Transduction t : transductions) {
			if(t instanceof DeterminerTransduction) {
				dt = (DeterminerTransduction) t;
				c = determinerHelper.getCorrection(dt);
				
				if(c != null) {
					corrections.add(c.clone());
				}
			}
		}
		
		return corrections;
	}
	
	public void cleanUp(List<Token> tokens) {
		DeterminerDao dao = new DeterminerDao();
		for(int i = 0; i < tokens.size()-2; i++) {
			if(tokens.get(i).getPartOfSpeech().equals("Z") && 
					(tokens.get(i).getHeadWord().toLowerCase().equals("a") || tokens.get(i).getHeadWord().toLowerCase().equals("an"))) {
				List<IndefiniteDto> indefinite = dao.getCorrectIndefinite(tokens.get(i+1).getHeadWord());
			
				if(!(indefinite.isEmpty() || 
						indefinite.get(0).getIndefinite().toLowerCase().equals(tokens.get(i).getHeadWord().toLowerCase()))) {
					tokens.get(i).setHeadWord(indefinite.get(0).getIndefinite());
				}
			}
		}
		
		
	}

}
