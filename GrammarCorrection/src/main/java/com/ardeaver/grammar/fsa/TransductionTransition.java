package com.ardeaver.grammar.fsa;

/**
 * The TransductionTransition class represents the current transition
 * between states in a Finite State Transducer. It carries with it the same information
 * as a Transition but with the current transduction as well.
 * 
 * @author Andrew Deaver
 * @version 1.0
 * @since 2017-02-06
 */
public class TransductionTransition extends Transition {
	// ***** INSTANCE VARIABLES *****
	
	// The current state of the transduction
	private Transduction currentTransduction;
	
	// ***** END INSTANCE VARIABLES *****
	
	/**
	 * The constructor for the Transduction Transition class
	 * 
	 * @param currentState The current state of the Finite State Transducer
	 * @param currentIndex The current index of the Finite State Transducer
	 * @param currentTransduction The transduction representing the data at the current state
	 */
	public TransductionTransition(int currentState, int currentIndex, Transduction currentTransduction) {
		super(currentState, currentIndex);
		this.currentTransduction = currentTransduction;
	}

	/**
	 * The getter for the current transduction
	 * 
	 * @return The current transduction
	 */
	public Transduction getCurrentTransduction() {
		return currentTransduction;
	}
}
