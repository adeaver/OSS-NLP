package com.ardeaver.grammar.correction;

import java.util.ArrayList;
import java.util.List;

import com.ardeaver.grammar.exceptions.StateMachineException;
import com.ardeaver.grammar.fsa.FiniteStateMachine;
import com.ardeaver.grammar.fsa.Transduction;
import com.ardeaver.grammar.preprocessing.Token;

/**
 * The CorrectionService class is the general class responsible for determining errors and
 * applying corrections. Since generating transductions for errors and applying the corrections
 * are the same, but transforming the transductions into corrections is not, this class is abstract
 * and to be implemented by all modules.
 * 
 * @author Andrew Deaver
 * @version 1.0
 * @since 2017-02-06
 */
public abstract class CorrectionService {
	private FiniteStateMachine fst;
	
	/**
	 * The constrcutor for the CorrectionService
	 * 
	 * @param fst The finite state machine used to generate transductions
	 */
	public CorrectionService(FiniteStateMachine fst) {
		this.fst = fst;
	}
	
	/**
	 * This is the main method to be called by the pipeline to invoke each module
	 * It generates transductions and then transforms them into corrections.
	 * 
	 * @param tokens The list of tokens that represents the input
	 * @return The new list of tokens (the sentence that was corrected)
	 */
	public List<Token> correctInput(List<Token> tokens) {
		List<Transduction> transductions = generateTransductions(tokens);
		List<Correction> corrections = transformTransductions(transductions);
		return applyCorrections(tokens, corrections);
	}
	
	/**
	 * This method runs the supplied finite state machine to identify areas in
	 * which errors are likely to occur. These transductions can be thought of as
	 * potential errors.
	 * 
	 * @param tokens The sentence before it has been corrected
	 * @return All of the potential errors and their spot in the sentence
	 */
	protected List<Transduction> generateTransductions(List<Token> tokens) {
		int index = 0;
		int stopIndex = tokens.size();
		
		List<Transduction> transductions = new ArrayList<Transduction>();
		Transduction translation = null;
		
		while(index <= stopIndex) {
			try {
				translation = fst.transduceInput(tokens, index);
			} catch (StateMachineException e) {
				e.printStackTrace();
				translation = null;
			}
			
			if(translation != null && translation.getBeginIndex() < translation.getEndIndex()) {
				transductions.add(translation);
				index = translation.getEndIndex() + 1;
			} else {
				index++;
			}
			
		}
		
		return transductions;
	}
	
	/**
	 * This method takes in a list of tokens and a list of corrections and puts the corrections in the
	 * appropriate place
	 * 
	 * @param tokens The sentence in its precorrected form
	 * @param corrections The corrections to be applied
	 * @return A new sentence, that is corrected
	 */
	private List<Token> applyCorrections(List<Token> tokens, List<Correction> corrections) {
		List<Token> output = new ArrayList<Token>();
		int correctionIndex = 0;
		boolean isCollecting = false;
		
		for(int i = 0; i < tokens.size(); i++) {
			if(correctionIndex < corrections.size() && corrections.get(correctionIndex).getBeginIndex() == i) {
				if(corrections.get(correctionIndex).getType() != CorrectionType.DELETION) {
					output.add(corrections.get(correctionIndex).getToken());
				}
				
				if(corrections.get(correctionIndex).getType() != CorrectionType.INSERTION) {
					isCollecting = true;
				} else {
					correctionIndex++;
				}
			}
			
			if(isCollecting) {
				if(corrections.get(correctionIndex).getEndIndex() == i) {
					isCollecting = false;
					correctionIndex++;
				}
				continue;
			} else {
				output.add(tokens.get(i));
			}
		}
		
		return output;
	}
	
	/**
	 * This method is to be implemented by all inheriting classes
	 * This method should dictate how transductions transform into corrections,
	 * which is different for all inheriting classes
	 * 
	 * @param transductions A list of the potential errors detected by the finite state machine
	 * @return A list of corrections to be applied to the sentence
	 */
	protected abstract List<Correction> transformTransductions(List<Transduction> transductions);
}
