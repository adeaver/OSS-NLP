package com.ardeaver.earley.entity;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class PairEntityTest {
	
	@Test
	public void testPairEntityRepresentations() {
		SingleEntity head = new SingleEntity("S");
		List<Entity> children = new ArrayList<Entity>();
		
		SingleEntity child1 = new SingleEntity("NP");
		SingleEntity child2 = new SingleEntity("VP");
		
		children.add(child1);
		children.add(child2);
		
		PairEntity pairEntity = new PairEntity(head, children, 0, 0, 0);
		
		
		SingleEntity headCompleted = new SingleEntity("NP");
		List<Entity> childrenCompleted = new ArrayList<Entity>();
		
		SingleEntity child1Completed = new SingleEntity("DET");
		SingleEntity child2Completed = new SingleEntity("NOM");
		
		childrenCompleted.add(child1Completed);
		childrenCompleted.add(child2Completed);
		
		PairEntity pairEntityCompleted = new PairEntity(headCompleted, childrenCompleted, 2, 0, 2);
		
		assertEquals("S -> NP VP", pairEntity.getEqualsString());
		assertEquals("NP -> DET NOM", pairEntityCompleted.getEqualsString());
	}
}
