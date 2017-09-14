package com.ardeaver.grammar.nounofnoun;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ardeaver.grammar.exceptions.StateMachineException;
import com.ardeaver.grammar.fsa.Transduction;
import com.ardeaver.grammar.preprocessing.Token;

public class NounOfNounStateMachineTest {
	private NounOfNounFiniteStateMachine fst;
	
	public NounOfNounStateMachineTest() {
		fst = new NounOfNounFiniteStateMachine();
	}
	
	@Test
	public void testNounOfNoun() throws StateMachineException {
		List<Token> input = new ArrayList<Token>();
		input.add(new Token("door"));
		input.add(new Token("of"));
		input.add(new Token("car"));
		
		input.get(0).setPartOfSpeech("NNS");
		input.get(1).setPartOfSpeech("IN");
		input.get(2).setPartOfSpeech("NNS");
		
		Transduction nounOfNounTransduction = fst.transduceInput(input);
		
		
		assertEquals("door of car", nounOfNounTransduction.getOutput());
	}
	
	@Test
	public void testNoNounOfNoun() throws StateMachineException {
		List<Token> input = new ArrayList<Token>();
		input.add(new Token("car"));
		input.add(new Token("door"));
		
		input.get(0).setPartOfSpeech("NNS");
		input.get(1).setPartOfSpeech("NNS");
		
		Transduction nounOfNounTransduction = fst.transduceInput(input);
		
		assertEquals(null, nounOfNounTransduction);
	}
}
