package com.ardeaver.grammar.pipeline;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.ardeaver.grammar.determiner.DeterminerService;
import com.ardeaver.grammar.nounofnoun.NounOfNounService;
import com.ardeaver.grammar.phrasal.PhrasalVerbCorrectionService;
import com.ardeaver.grammar.postagger.PartOfSpeechTagger;
import com.ardeaver.grammar.prepositional.noun.PrepositionalNounService;
import com.ardeaver.grammar.prepositional.verb.PrepositionalVerbService;
import com.ardeaver.grammar.preprocessing.Token;
import com.ardeaver.grammar.preprocessing.Tokenizer;
import com.ardeaver.grammar.sva.SubjectVerbAgreementCorrectionService;

public class Corrector {
	private NounOfNounService nounOfNounService;
	private PrepositionalNounService prepositionalNounService;
	private PrepositionalVerbService prepositionalVerbService;
	private SubjectVerbAgreementCorrectionService svaCorrectionService;
	//private PhrasalVerbCorrectionService phrasalVerbService;
	//private DeterminerService determinerService;
	private PartOfSpeechTagger tagger;
	private SentenceProbabilityService probabilityService;
	
	public Corrector() {
		nounOfNounService = new NounOfNounService();
		prepositionalNounService = new PrepositionalNounService();
		prepositionalVerbService = new PrepositionalVerbService();
		svaCorrectionService = new SubjectVerbAgreementCorrectionService();
		//phrasalVerbService = new PhrasalVerbCorrectionService();
		//determinerService = new DeterminerService();
		tagger = new PartOfSpeechTagger();
		probabilityService = new SentenceProbabilityService();
	}
	
	public Correction correct(String input) {
		List<Token> tokenized = Tokenizer.tokenizeInput(input);
		tokenized = tagger.tagSentence(tokenized);
		
		System.out.println("Handling Noun of noun errors...");
		List<Token> nounOfNoun = nounOfNounService.correctInput(tokenized);
		
		System.out.println("Prepositional noun phrases...");
		tokenized = tagger.tagSentence(nounOfNoun);
		List<Token> prepositionalNoun = prepositionalNounService.correctInput(tokenized);
		
		//System.out.println("Phrasal verbs...");
		//tokenized = tagger.tagSentence(prepositionalNoun);
		//List<Token> phrasalVerb = phrasalVerbService.correctInput(tokenized);
		
		System.out.println("Prepositional verb phrases...");
		tokenized = tagger.tagSentence(prepositionalNoun);
		List<Token> prepositionalVerb = prepositionalVerbService.correctInput(tokenized);
		
		//System.out.println("Determiners...");
		//tokenized = tagger.tagSentence(prepositionalVerb);
		//List<Token> determiners = determinerService.correctInput(tokenized);
		//determinerService.cleanUp(determiners);
		
		System.out.println("Subject verb agreement...");
		tokenized = tagger.tagSentence(prepositionalVerb);
		List<Token> sva = svaCorrectionService.correctInput(tokenized);
		
		String sentence = Tokenizer.getSentence(sva);
		
		return new Correction(sentence, probabilityService.getSentenceProbability(sentence));
	}
	
	public Set<Correction> getOrderedCorrections(List<String> cor) {
		Set<Correction> orderedCorrections = new TreeSet<Correction>(new CorrectionComparator());
		
		for(String sentence : cor) {
			orderedCorrections.add(new Correction(sentence, probabilityService.getSentenceProbability(sentence)));
		}
		
		return orderedCorrections;
	}

}
