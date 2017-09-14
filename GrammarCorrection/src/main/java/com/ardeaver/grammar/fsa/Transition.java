package com.ardeaver.grammar.fsa;

/**
 * The Transition class represents a new state for a 
 * Finite State Machine. These are produced by the transition table and
 * held in a Stack to keep track of what the next state to go to is.
 * 
 * @author Andrew Deaver
 * @version 1.0
 * @since 2017-02-06
 */
public class Transition {
	// ***** INSTANCE VARIABLES *****
	
	// The current state and current index of the Finite State Machine
	private int currentState, currentIndex;
	
	// ***** END INSTANCE VARIABLES *****
	
	/**
	 * Constructor for the Transition class
	 * 
	 * @param currentState The current state of the Finite State Machine
	 * @param currentIndex The current index of the Finite State Machine
	 */
	public Transition(int currentState, int currentIndex) {
		this.currentState = currentState;
		this.currentIndex = currentIndex;
	}

	/**
	 * The getter for the current state
	 * 
	 * @return The current state of the finite state machine
	 */
	public int getCurrentState() {
		return currentState;
	}

	/**
	 * The getter for the current index
	 * 
	 * @return The current index of the finite state machine
	 */
	public int getCurrentIndex() {
		return currentIndex;
	}
}
