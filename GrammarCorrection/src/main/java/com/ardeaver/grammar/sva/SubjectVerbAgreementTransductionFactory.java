package com.ardeaver.grammar.sva;

import com.ardeaver.grammar.fsa.Transduction;
import com.ardeaver.grammar.fsa.TransductionFactory;

/**
 * This is the implementation of TransductionFactory used by the SVA module
 * 
 * @author Andrew Deaver
 * @version 1.0
 * @since 2017-02-28
 */
public class SubjectVerbAgreementTransductionFactory extends TransductionFactory {

	@Override
	public Transduction getTransduction() {
		return new SubjectVerbAgreementTransduction();
	}

}
