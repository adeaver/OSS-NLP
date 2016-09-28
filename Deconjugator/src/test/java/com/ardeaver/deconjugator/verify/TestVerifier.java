package com.ardeaver.deconjugator.verify;

import org.junit.Test;

import com.ardeaver.deconjugator.fst.RegularVerbFST;
import com.ardeaver.deconjugator.util.DeconstructedVerb;

public class TestVerifier {
	@Test
	public void testVerfication() {
		DeconstructedVerb verb = RegularVerbFST.unconjugate("hablo");
		System.out.println(Verifier.returnMostLikely(verb));
	}
	
	@Test
	public void testVerificationAgain() {
		DeconstructedVerb verb = RegularVerbFST.unconjugate("dejo");
		System.out.println(Verifier.returnMostLikely(verb));
	}
}
