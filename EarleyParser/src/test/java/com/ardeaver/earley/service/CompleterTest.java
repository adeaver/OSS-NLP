package com.ardeaver.earley.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ardeaver.earley.entity.Entity;
import com.ardeaver.earley.entity.PairEntity;
import com.ardeaver.earley.entity.SingleEntity;

public class CompleterTest {
	private Completer completer;
	
	public CompleterTest() {
		completer = new Completer();
	}
	
	@Test
	public void testBasicCompletion() {
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
		
		List<PairEntity> completions = completer.complete(pairs, pairEntityCompleted);
		
		assertEquals(1, completions.size());
		assertEquals("( S ( NP DET NOM ) VP )", completions.get(0).toString());
		assertEquals(0, completions.get(0).getStartIndex());
		assertEquals(1, completions.get(0).getStar());
		assertEquals(2, completions.get(0).getEndIndex());
		assertEquals("VP", completions.get(0).getNext().getHead());
	}
}
