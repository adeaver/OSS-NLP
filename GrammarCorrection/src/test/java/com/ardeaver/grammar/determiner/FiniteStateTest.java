package com.ardeaver.grammar.determiner;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ardeaver.grammar.exceptions.StateMachineException;
import com.ardeaver.grammar.fsa.Transduction;
import com.ardeaver.grammar.preprocessing.Token;

public class FiniteStateTest {
	private DeterminerFiniteStateMachine fst;
	
	public FiniteStateTest() {
		fst = new DeterminerFiniteStateMachine();
	}
	
	@Test
	public void findNounPhrase() throws StateMachineException {
		List<Token> input = new ArrayList<Token>();
		input.add(new Token("both"));
		input.add(new Token("the"));
		input.add(new Token("red"));
		input.add(new Token("cars"));
		
		input.get(0).setPartOfSpeech("PDT");
		input.get(1).setPartOfSpeech("DT");
		input.get(2).setPartOfSpeech("JJ");
		input.get(3).setPartOfSpeech("NN");
		
		Transduction determinerTransduction = fst.transduceInput(input);
		
		assertEquals("the cars", determinerTransduction.getOutput());
		assertEquals(3, determinerTransduction.getEndIndex());
	}
	
	@Test
	public void findNounPhraseNoModifiers() throws StateMachineException {
		List<Token> input = new ArrayList<Token>();
		input.add(new Token("car"));
		
		input.get(0).setPartOfSpeech("NN");
		
		Transduction determinerTransduction = fst.transduceInput(input);
		
		assertEquals("car", determinerTransduction.getOutput());
		assertEquals(0, determinerTransduction.getEndIndex());
	}
	
	@Test
	public void findNounPhraseDoubleNoun() throws StateMachineException {
		List<Token> input = new ArrayList<Token>();
		input.add(new Token("the"));
		input.add(new Token("component"));
		input.add(new Token("failures"));
		
		input.get(0).setPartOfSpeech("DT");
		input.get(1).setPartOfSpeech("NN");
		input.get(2).setPartOfSpeech("NN");
		
		Transduction determinerTransduction = fst.transduceInput(input);
		
		assertEquals("the failures", determinerTransduction.getOutput());
		assertEquals(2, determinerTransduction.getEndIndex());
	}
	
	@Test
	public void findNounPhrasePronoun() throws StateMachineException {
		List<Token> input = new ArrayList<Token>();
		input.add(new Token("that"));
		input.add(new Token("they"));
		
		input.get(0).setPartOfSpeech("IN");
		input.get(1).setPartOfSpeech("PRP");
		
		Transduction determinerTransduction = fst.transduceInput(input);
		
		assertEquals("they", determinerTransduction.getOutput());
		assertEquals(1, determinerTransduction.getEndIndex());
	}
}
