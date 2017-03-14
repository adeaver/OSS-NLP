package com.ardeaver.earley.parser;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ardeaver.earley.entity.Entity;
import com.ardeaver.earley.entity.PairEntity;
import com.ardeaver.earley.entity.SingleEntity;

public class EarleyParserTest {
	private EarleyParser parser;
	
	public EarleyParserTest() {
		parser = new EarleyParser();
	}
	
	@Test
	public void testBasic() {
		String input = "Wake him up";
		
		List<PairEntity> parses = parser.parseSentence(input);
		System.out.println(parses.toString());
	}
	
	@Test
	public void duplicatesTest() {
		SingleEntity head = new SingleEntity("S");
		List<Entity> children = new ArrayList<Entity>();
		
		SingleEntity child1 = new SingleEntity("NP");
		SingleEntity child2 = new SingleEntity("VP");
		
		children.add(child1);
		children.add(child2);
		
		PairEntity pairEntity = new PairEntity(head, children, 0, 0, 0);
		
		List<PairEntity> pairs = new ArrayList<PairEntity>();
		pairs.add(pairEntity);
		
		SingleEntity headCompleted = new SingleEntity("NP");
		List<Entity> childrenCompleted = new ArrayList<Entity>();
		
		SingleEntity child1Completed = new SingleEntity("DET");
		SingleEntity child2Completed = new SingleEntity("NOM");
		
		childrenCompleted.add(child1Completed);
		childrenCompleted.add(child2Completed);
		
		PairEntity pairEntityCompleted = new PairEntity(headCompleted, childrenCompleted, 2, 0, 2);
		
		List<PairEntity> pairs2 = new ArrayList<PairEntity>();
		pairs2.add(pairEntityCompleted);
		pairs2.add(pairEntity.clone());
		
		parser.filterAdditions(pairs, pairs2);
		
		assertEquals(2, pairs.size());
	}
}
