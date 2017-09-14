package com.ardeaver.grammar.sva;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.ardeaver.grammar.exceptions.StateMachineException;
import com.ardeaver.grammar.preprocessing.Token;
import com.ardeaver.grammar.test.util.TestTokenizer;

public class SubjectVerbAgreementFiniteStateTest {
	private SubjectVerbAgreementFiniteStateMachine subjectVerbAgreementTransducer = new SubjectVerbAgreementFiniteStateMachine();
	
	@Test
	public void test_parsedNounPhrase() {
		List<Token> input = TestTokenizer.tokenizeInput("jury_NN said_VBD it_PPS did_DOD find_VB that_CS many_AP of_IN Georgia's_NP$ registration_NN "
				+ "and_CC election_NN laws_NNS are_BER outmoded_JJ or_CC inadequate_JJ and_CC often_RB ambiguous_JJ ._.");
		SubjectVerbAgreementTransduction transduction = new SubjectVerbAgreementTransduction();
		try {
			transduction = (SubjectVerbAgreementTransduction) subjectVerbAgreementTransducer.transduceInput(input);
		} catch (StateMachineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals("jury", transduction.getHeadNoun().getHeadWord());
		assertEquals("said", transduction.getVerbsAdverbs().get(0).getHeadWord());
	}
	
	@Test
	public void test_parsedNounPhrase2() {
		List<Token> input = TestTokenizer.tokenizeInput("City_NN Purchasing_VBG Department_NN "
				+ "is_BEZ lacking_VBG in_IN experienced_VBN clerical_JJ personnel_NNS as_CS a_AT result_NN of_IN city_NN personnel_NNS policies_NNS ._.");
		
		SubjectVerbAgreementTransduction transduction = new SubjectVerbAgreementTransduction();
		try {
			transduction = (SubjectVerbAgreementTransduction) subjectVerbAgreementTransducer.transduceInput(input);
		} catch (StateMachineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals("City", transduction.getHeadNoun().getHeadWord());
		assertEquals("is", transduction.getVerbsAdverbs().get(0).getHeadWord());
	}
	
	@Test
	public void test_incorrectPhrase() {
		List<Token> input = TestTokenizer.tokenizeInput("She_PPS has_HVZ patient_NN when_WRB she_PPS teach_VB me_PPO difficult_JJ word_NN");
		
		SubjectVerbAgreementTransduction transduction = new SubjectVerbAgreementTransduction();
		try {
			transduction = (SubjectVerbAgreementTransduction) subjectVerbAgreementTransducer.transduceInput(input);
		} catch (StateMachineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals("She", transduction.getHeadNoun().getHeadWord());
		assertEquals("has", transduction.getVerbsAdverbs().get(0).getHeadWord());
	}
	
	@Test
	public void test_noNounPhrase() {
		List<Token> input = TestTokenizer.tokenizeInput("Share_VBZ that_CS book_NN");
		
		SubjectVerbAgreementTransduction transduction = new SubjectVerbAgreementTransduction();
		try {
			transduction = (SubjectVerbAgreementTransduction) subjectVerbAgreementTransducer.transduceInput(input);
		} catch (StateMachineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(null, transduction);
	}
	
	@Test
	public void test_multiClause() {
		List<Token> input = TestTokenizer.tokenizeInput("She_NN said_VBD that_CS I_PPSS don't_DOD* know_VBZ what_WDT I_PPSS want_VBZ Bill_NN to_TO do_DO");
		
		SubjectVerbAgreementTransduction transduction = new SubjectVerbAgreementTransduction();
		try {
			transduction = (SubjectVerbAgreementTransduction) subjectVerbAgreementTransducer.transduceInput(input);
		} catch (StateMachineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals("She", transduction.getHeadNoun().getHeadWord());
		assertEquals("said", transduction.getVerbsAdverbs().get(0).getHeadWord());
	}
	
	@Test
	public void test_future() {
		List<Token> input = TestTokenizer.tokenizeInput("He_NN will_MD go_VB to_TO the_DT mall_NN");
		
		SubjectVerbAgreementTransduction transduction = new SubjectVerbAgreementTransduction();
		try {
			transduction = (SubjectVerbAgreementTransduction) subjectVerbAgreementTransducer.transduceInput(input);
		} catch (StateMachineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals("He", transduction.getHeadNoun().getHeadWord());
		assertEquals("will", transduction.getVerbsAdverbs().get(0).getHeadWord());
		assertEquals(2, transduction.getVerbsAdverbs().size());
	}
	
	@Test
	public void test_multinoun() {
		List<Token> input = TestTokenizer.tokenizeInput("She_PRP and_CC I_PRP has_HVZ a_DT cake_NN");
		SubjectVerbAgreementTransduction transduction = new SubjectVerbAgreementTransduction();
		try {
			transduction = (SubjectVerbAgreementTransduction) subjectVerbAgreementTransducer.transduceInput(input);
		} catch (StateMachineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(transduction != null);
		assertEquals("they", transduction.getHeadNoun().getHeadWord());
		assertEquals("has", transduction.getVerbsAdverbs().get(0).getHeadWord());
		assertEquals(1, transduction.getVerbsAdverbs().size());
	}
	
	@Test
	public void test_triple() {
		List<Token> input = TestTokenizer.tokenizeInput("She_PRP would_MD have_HVZ gone_VBD");
		SubjectVerbAgreementTransduction transduction = new SubjectVerbAgreementTransduction();
		try {
			transduction = (SubjectVerbAgreementTransduction) subjectVerbAgreementTransducer.transduceInput(input);
		} catch (StateMachineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(transduction != null);
		assertEquals("She", transduction.getHeadNoun().getHeadWord());
		assertEquals("would", transduction.getVerbsAdverbs().get(0).getHeadWord());
		assertEquals(3, transduction.getVerbsAdverbs().size());
	}
	
	@Test
	public void test_question() {
		List<Token> input = TestTokenizer.tokenizeInput("Do_DO you_PRP have_HV the_DT time_NN");
		SubjectVerbAgreementTransduction transduction = new SubjectVerbAgreementTransduction();
		try {
			transduction = (SubjectVerbAgreementTransduction) subjectVerbAgreementTransducer.transduceInput(input);
		} catch (StateMachineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(transduction != null);
		assertEquals("you", transduction.getHeadNoun().getHeadWord());
		assertEquals("Do", transduction.getVerbsAdverbs().get(0).getHeadWord());
		assertEquals(2, transduction.getVerbsAdverbs().size());
		assertEquals("have", transduction.getVerbsAdverbs().get(1).getHeadWord());
	}
	
	@Test
	public void test_question2() {
		List<Token> input = TestTokenizer.tokenizeInput("Are_BE you_PRP going_VBG this_DT time_NN");
		SubjectVerbAgreementTransduction transduction = new SubjectVerbAgreementTransduction();
		try {
			transduction = (SubjectVerbAgreementTransduction) subjectVerbAgreementTransducer.transduceInput(input);
		} catch (StateMachineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(transduction != null);
		assertEquals("you", transduction.getHeadNoun().getHeadWord());
		assertEquals("Are", transduction.getVerbsAdverbs().get(0).getHeadWord());
		assertEquals(2, transduction.getVerbsAdverbs().size());
		assertEquals("going", transduction.getVerbsAdverbs().get(1).getHeadWord());
	}
	
	@Test
	public void test_adverbs() {
		List<Token> input = TestTokenizer.tokenizeInput("I_PRP have_HVZ also_RB been_BED running_VBG");
		SubjectVerbAgreementTransduction transduction = new SubjectVerbAgreementTransduction();
		try {
			transduction = (SubjectVerbAgreementTransduction) subjectVerbAgreementTransducer.transduceInput(input);
		} catch (StateMachineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(transduction != null);
		assertEquals(4, transduction.getVerbsAdverbs().size());
		assertEquals("have", transduction.getVerbsAdverbs().get(0).getHeadWord());
		assertEquals("also", transduction.getVerbsAdverbs().get(1).getHeadWord());
		assertEquals("been", transduction.getVerbsAdverbs().get(2).getHeadWord());
		assertEquals("running", transduction.getVerbsAdverbs().get(3).getHeadWord());
	}
}
