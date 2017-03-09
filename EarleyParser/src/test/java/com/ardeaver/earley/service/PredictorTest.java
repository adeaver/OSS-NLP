package com.ardeaver.earley.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ardeaver.earley.entity.Entity;
import com.ardeaver.earley.entity.PairEntity;
import com.ardeaver.earley.entity.SingleEntity;

public class PredictorTest {
	private Predictor predictor;
	
	public PredictorTest() {
		predictor = new Predictor();
	}
	
	@Test
	public void predictForNounPhrase() {
		SingleEntity head = new SingleEntity("S");
		List<Entity> children = new ArrayList<Entity>();
		
		SingleEntity child1 = new SingleEntity("NP");
		SingleEntity child2 = new SingleEntity("VP");
		
		children.add(child1);
		children.add(child2);
		
		PairEntity pairEntity = new PairEntity(head, children, 0, 0, 0);
		
		List<PairEntity> pair = predictor.predict(pairEntity);
		
		for(PairEntity p : pair) {
			assertEquals("NP", p.getHead());
			assertEquals(0, p.getStartIndex());
			assertEquals(0, p.getEndIndex());
		}
		
		assertEquals("( S NP VP )", pairEntity.toString());
	}
}
