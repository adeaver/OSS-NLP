package com.ardeaver.grammar.fsa;

import java.util.ArrayDeque;
import java.util.List;

import com.ardeaver.grammar.exceptions.StateMachineException;
import com.ardeaver.grammar.preprocessing.Token;

/**
 * The FiniteStateMachine class is the core of most modules. It is used
 * to identify potential errors, which it returns as transductions.
 * This is implemented by each module depending on how errors are identified for that
 * module.
 * 
 * @author Andrew Deaver
 * @version 1.0
 * @since 2017-02-06
 */
public abstract class FiniteStateMachine {
	// ***** INSTANCE VARIABLES *****
	
	// The transition table on which the finite state machine is based
	private TransitionTable transitionTable;
	
	// The input converter to converter each token into an input type
	private InputConverter inputConverter;
	
	// A list of all the accepting states
	private List<Integer> acceptingStates;
	
	// A factory class for transductions
	private TransductionFactory transductionFactory;
	
	// Switch for determining whether or not the state machine
	// ends as soon as it enters the accepting state or whether it can continue.
	private boolean endMidAccept;
	
	/**
	 * The constructor for Finite State Automata used for validation
	 * Classes invoking this constructor cannot be used to generate transductions
	 * 
	 * @param transitionTable The Transition Table to be used for this FSA
	 * @param inputConverter The input converter used for this FSA
	 * @param endMidAccept Whether or not this FSA ends as soon as it enters an accepting state
	 */
	public FiniteStateMachine(TransitionTable transitionTable, InputConverter inputConverter, boolean endMidAccept) {
		this.transitionTable = transitionTable;
		this.inputConverter = inputConverter;
		this.acceptingStates = this.transitionTable.getAcceptingStates();
		this.transductionFactory = null;
		this.endMidAccept = endMidAccept;
	}
	
	/**
	 * Constructor for FSA with default value for endMidAccept (true)
	 * 
	 * @param transitionTable The Transition Table to be used for this FSA
	 * @param inputConverter The input converter used for this FSA
	 */
	public FiniteStateMachine(TransitionTable transitionTable, InputConverter inputConverter) {
		this(transitionTable, inputConverter, true);
	}
	
	/**
	 * Constructor for FST with the default value for endMidAccept (true)
	 * 
	 * @param transitionTable The Transition Table to be used for this FST
	 * @param inputConverter The input converter used for this FST
	 * @param transductionFactory The transduction factory used for this FST
	 */
	public FiniteStateMachine(TransitionTable transitionTable, InputConverter inputConverter, TransductionFactory transductionFactory) {
		this(transitionTable, inputConverter, true);
		this.transductionFactory = transductionFactory;
	}
	
	/**
	 * This is the constructor for Finite State Transducers. These are used by
	 * most (if not all) modules to find potential errors.
	 * 
	 * @param transitionTable The Transition Table to be used for this FST
	 * @param inputConverter The input converter used for this FST
	 * @param transductionFactory The transduction factory used for this FST
	 * @param endMidAccept Whether or not this FSA ends as soon as it enters an accepting state
	 */
	public FiniteStateMachine(TransitionTable transitionTable, InputConverter inputConverter, 
			TransductionFactory transductionFactory, boolean endMidAccept) {
		this(transitionTable, inputConverter, endMidAccept);
		this.transductionFactory = transductionFactory;
	}
	
	/**
	 * This determines if the input string is valid. This method is currently
	 * unimplemented by any module.
	 * 
	 * @param tokens The string of tokens to validate
	 * @return Whether or not this is valid.
	 */
	public boolean validate(List<Token> tokens) {
		ArrayDeque<Transition> agenda = new ArrayDeque<Transition>();
		List<Integer> nextStates;
		int currentState = 0, currentIndex = 0, inputType = 0;
		Transition nextTransition;
		
		while(currentIndex < tokens.size()) {
			inputType = inputConverter.convertInputToType(tokens.get(currentIndex));
			nextStates = transitionTable.getPossibleStates(currentState, inputType);
			
			if(!nextStates.isEmpty()) {
				for(int nextState : nextStates) {
					agenda.push(new Transition(nextState, currentIndex+1));
				}
				
				nextTransition = agenda.pop();
				currentIndex = nextTransition.getCurrentIndex();
				currentState = nextTransition.getCurrentState();
			} else {
				if(acceptingStates.contains(currentState)) {
					return true;
				}
				else {
					if(!agenda.isEmpty()) {
						nextTransition = agenda.pop();
						currentIndex = nextTransition.getCurrentIndex();
						currentState = nextTransition.getCurrentState();
					} else {
						break;
					}
				}
			}
		}
		
		return acceptingStates.contains(currentState);
	}

	/**
	 * This method is used to determine if there are potential errors
	 * by transduce the input sentence into a transduction. This uses a DFS
	 * method of traversing through non-deterministic FSTs.
	 * 
	 * @param tokens The input sentence
	 * @param startIndex The index in the sentence to start at
	 * @return A transduction representing a potential error
	 * @throws StateMachineException This will be thrown if this Finite State Machine does not support transduction
	 */
	public Transduction transduceInput(List<Token> tokens, int startIndex) throws StateMachineException {
		
		if(transductionFactory == null) {
			throw new StateMachineException();
		}
		
		ArrayDeque<TransductionTransition> agenda = new ArrayDeque<TransductionTransition>();
		List<Integer> nextStates;
		int currentIndex = startIndex, currentState = 0, inputType = 0;
		Transduction currentTransduction = transductionFactory.getTransduction();
		Transduction nextTransduction;
		TransductionTransition nextTransition;
		
		while(currentIndex < tokens.size()) {
			inputType = inputConverter.convertInputToType(tokens.get(currentIndex));
			currentTransduction.addToOutput(tokens.get(currentIndex), currentState, currentIndex);
			nextStates = transitionTable.getPossibleStates(currentState, inputType);
			
			if(!nextStates.isEmpty() && currentIndex <= tokens.size() - 1) {
				nextTransduction = transductionFactory.getTransduction();
				nextTransduction.copyState(currentTransduction);
				
				for(int i = nextStates.size()-1; i >= 0; i--) {
					int nextState = nextStates.get(i);
					agenda.push(new TransductionTransition(nextState, currentIndex+1, nextTransduction));
				}
				
				nextTransition = agenda.pop();
				currentTransduction = nextTransition.getCurrentTransduction();
				currentIndex = nextTransition.getCurrentIndex();
				currentState = nextTransition.getCurrentState();
				
				if(endMidAccept && acceptingStates.contains(currentState)) {
					return currentTransduction;
				}
			} else {
				if(acceptingStates.contains(currentState)) {
					return currentTransduction;
				}
				else {
					if (!agenda.isEmpty()) {
						nextTransition = agenda.pop();
						currentTransduction = nextTransition.getCurrentTransduction();
						currentIndex = nextTransition.getCurrentIndex();
						currentState = nextTransition.getCurrentState();
					} else {
						break;
					}
				}
			}
		}
		
		while(!agenda.isEmpty()) {
			if(acceptingStates.contains(currentState)) {
				return currentTransduction;
			}
			else {
				nextTransition = agenda.pop();
				currentTransduction = nextTransition.getCurrentTransduction();
				currentIndex = nextTransition.getCurrentIndex();
				currentState = nextTransition.getCurrentState();
			}
		}
		
		return acceptingStates.contains(currentState) ? currentTransduction : null;
	}
	
	/**
	 * Transduces input starting at the beginning
	 * 
	 * @param tokens The input sentence
	 * @return The transduction from the beginning
	 * @throws StateMachineException
	 */
	public Transduction transduceInput(List<Token> tokens) throws StateMachineException {
		return transduceInput(tokens, 0);
	}
}
