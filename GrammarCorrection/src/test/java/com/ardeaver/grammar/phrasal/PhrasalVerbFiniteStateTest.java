package com.ardeaver.grammar.phrasal;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ardeaver.grammar.exceptions.StateMachineException;
import com.ardeaver.grammar.preprocessing.Token;

public class PhrasalVerbFiniteStateTest {
	private PhrasalVerbFiniteStateMachine pvFST;
	
	public PhrasalVerbFiniteStateTest() {
		pvFST = new PhrasalVerbFiniteStateMachine();
	}
	
	@Test
	public void testFindsPV() throws StateMachineException {
		//String input = "share_VB me_PRP with_IN knowledge_NN";
		List<Token> input = new ArrayList<Token>();
		input.add(new Token("share"));
		input.add(new Token("me"));
		input.add(new Token("with"));
		input.add(new Token("knowledge"));
		
		input.get(0).setPartOfSpeech("VB");
		input.get(1).setPartOfSpeech("PRP");
		input.get(2).setPartOfSpeech("IN");
		input.get(3).setPartOfSpeech("NN");
		
		PhrasalVerbTransduction pvt = (PhrasalVerbTransduction) pvFST.transduceInput(input);
		
		assertEquals("share", pvt.getMainVerb().getHeadWord());
		assertEquals(2, pvt.getNouns().size());
		assertEquals("with", pvt.getPreps().get(0).getHeadWord());
		assertEquals(PhrasalVerbTransduction.PREPOSITION_TWO_NOUNS.intValue(), pvt.getType());
	}
}
