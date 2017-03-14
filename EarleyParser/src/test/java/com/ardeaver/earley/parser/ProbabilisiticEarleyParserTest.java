package com.ardeaver.earley.parser;

import java.util.List;
import java.util.TreeSet;

import org.junit.Test;

import com.ardeaver.earley.probabilistic.entity.ProbabilisticPairEntity;

public class ProbabilisiticEarleyParserTest {
	private ProbabilisiticEarleyParser parser;
	
	public ProbabilisiticEarleyParserTest() {
		parser = new ProbabilisiticEarleyParser();
	}
	
	@Test
	public void testBasic() {
		String input = "Wake him up";
		
		List<ProbabilisticPairEntity> parses = parser.parseInput(input);
		TreeSet<ProbabilisticPairEntity> orderedParses = new TreeSet<ProbabilisticPairEntity>();
		orderedParses.addAll(parses);
		
		System.out.println(orderedParses.first());
	}
}
