package com.ardeaver.grammar.sva;

import com.ardeaver.grammar.fsa.FiniteStateMachine;

/**
 * The finite state machine for the subject verb agreement module
 * 
 * @author Andrew Deaver
 * @version 1.0
 * @since 2017-02-28
 */
public class SubjectVerbAgreementFiniteStateMachine extends FiniteStateMachine {
	
	// ***** INSTANCE VARIABLES *****
	private static final SubjectVerbAgreementTransitionTable TRANSITION_TABLE;
	private static final SubjectVerbAgreementInputConverter INPUT_CONVERTER;
	private static final SubjectVerbAgreementTransductionFactory TRANSDUCTION_FACTORY;
	// ***** END INSTANCE VARIABLES *****

	// static constructor - added for legibility
	static {
		TRANSITION_TABLE = new SubjectVerbAgreementTransitionTable();
		INPUT_CONVERTER = new SubjectVerbAgreementInputConverter();
		TRANSDUCTION_FACTORY = new SubjectVerbAgreementTransductionFactory();
	}

	/**
	 * Constructor
	 */
	public SubjectVerbAgreementFiniteStateMachine() {
		super(TRANSITION_TABLE, INPUT_CONVERTER, TRANSDUCTION_FACTORY);
	}

}
