package com.ardeaver.grammar.exceptions;

public class StateMachineException extends Exception {
	private static final long serialVersionUID = 1L;

	public StateMachineException() {
		super("State Machine Does Not Support This Operation");
	}
	
	public StateMachineException(String errorMessage) {
		super(errorMessage);
	}
}
