package com.ardeaver.grammar.fsa;

import java.util.ArrayList;
import java.util.List;

/**
 * The TransitionTable class represents the behavior of the finite state machine.
 * Each finite state machine must have a transition table dictating how and where to move
 * from each state given each type of input. Each module must implement a transition table
 * if it is to implement a finite state machine.
 * 
 * @author Andrew Deaver
 * @version 1.0
 * @since 2017-02-06
 */
public abstract class TransitionTable {
	// ***** INSTANCE VARIABLES *****
	
	// The table containing all of the possible transitions.
	// It is organized by state (rows) and input (columns). Each entry is
	// a list of possible states.
	private int[][][] table;
	
	// ***** END INSTANCE VARIABLES *****
	
	/**
	 * The constructor for the transition table. This takes in a table of all
	 * possible transitions. It is organized by state (rows) and input (columns). 
	 * Each entry is a list of possible states.
	 * 
	 * @param table The transition table
	 */
	public TransitionTable(int[][][] table) {
		this.table = table;
	}
	
	/**
	 * Returns all the possible states that the finite state machine can
	 * proceed to given the current state and current input type. It will
	 * return an empty list if it can't find any possible states.
	 * 
	 * @param currentState The current state of the finite state machine
	 * @param input The input group to check
	 * @return A list of all possible states (can be empty)
	 */
	public List<Integer> getPossibleStates(int currentState, int input) {
		List<Integer> possibleStates = new ArrayList<Integer>();
		
		int[] nextStates = table[currentState][input];
		
		if(nextStates != null) {
			for(int nState : nextStates) {
				possibleStates.add(nState);
			}
		}
		
		return possibleStates;
	}
	
	/**
	 * Returns a list of all possible accepting states. This is to
	 * be implemented by all inheriting classes.
	 * 
	 * @return A list of all possible classes
	 */
	public abstract List<Integer> getAcceptingStates();
}
