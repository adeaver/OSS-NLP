package com.ardeaver.transition;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Test;

import com.ardeaver.fsa.util.TypeConverter;

public class EnglishNumberTransitionTableTest {
	Optional<List<Integer>> aStates, bStates, cStates, dStates, eStates;
	
	@Test
	public void testZeroState() {
		getStates(0);
		
		assertFullStates(aStates, bStates, cStates);
		assertEmptyStates(dStates, eStates);
		
		assertContains(aStates, 1, 14);
		assertContains(bStates, 2, 14);
		assertContains(cStates, 3, 14);
	}
	
	@Test
	public void testOneState() {
		getStates(1);
		
		assertFullStates(dStates, eStates);
		assertEmptyStates(aStates, bStates, cStates);
		
		assertContains(dStates, 6, 14);
		assertContains(eStates, 5, 14);
	}
	
	@Test
	public void testTwoState() {
		getStates(2);
		
		assertFullStates(eStates);
		assertEmptyStates(aStates, bStates, cStates, dStates);
		
		assertContains(eStates, 5, 14);
	}
	
	@Test
	public void testThreeState() {
		getStates(3);
		
		assertFullStates(aStates);
		assertEmptyStates(bStates, cStates, dStates, eStates);
		
		assertContains(aStates, 4, 14);
	}
	
	@Test
	public void testFourState() {
		getStates(4);
		
		assertFullStates(eStates);
		assertEmptyStates(aStates, bStates, cStates, dStates);
		
		assertContains(eStates, 14);
	}
	
	@Test
	public void testFiveState() {
		getStates(5);
		
		assertFullStates(aStates, bStates, cStates);
		assertEmptyStates(dStates, eStates);
		
		assertContains(aStates, 10, 14);
		assertContains(bStates, 14);
		assertContains(cStates, 11, 14);
	}
	
	@Test
	public void testSixState() {
		getStates(6);
		
		assertFullStates(aStates, bStates, cStates, eStates);
		assertEmptyStates(dStates);
		
		assertContains(aStates, 8, 14);
		assertContains(bStates, 9, 14);
		assertContains(cStates, 7, 14);
		assertContains(eStates, 5, 14);
	}
	
	@Test
	public void testSevenState() {
		getStates(7);
		
		assertFullStates(aStates, eStates);
		assertEmptyStates(bStates, cStates, dStates);
		
		assertContains(aStates, 8);
		assertContains(eStates, 5, 14);
	}
	
	@Test
	public void testEightState() {
		getStates(8);
		
		assertFullStates(eStates);
		assertEmptyStates(aStates, bStates, cStates, dStates);
		
		assertContains(eStates, 5, 14);
	}
	
	@Test
	public void testNineState() {
		getStates(9);
		
		assertFullStates(eStates);
		assertEmptyStates(aStates, bStates, cStates, dStates);
		
		assertContains(eStates, 5, 14);
	}
	
	@Test
	public void testTenState() {
		getStates(10);
		
		assertFullStates(dStates);
		assertEmptyStates(aStates, bStates, cStates, eStates);
		
		assertContains(dStates, 12, 14);
	}
	
	@Test
	public void testElevenState() {
		getStates(11);
		
		assertFullStates(aStates);
		assertEmptyStates(bStates, cStates, dStates, eStates);
		
		assertContains(aStates, 14);
	}
	
	@Test
	public void testTwelveState() {
		getStates(12);
		
		assertFullStates(aStates, bStates, cStates);
		assertEmptyStates(dStates, eStates);
		
		assertContains(aStates, 14);
		assertContains(bStates, 14);
		assertContains(cStates, 13, 14);
	}
	
	@Test
	public void testThirteenState() {
		getStates(13);
		
		assertFullStates(aStates);
		assertEmptyStates(bStates, cStates, dStates, eStates);
		
		assertContains(aStates, 14);
	}
	
	public void getStates(int currentState) {
		aStates = EnglishNumberTransitionTable.getPossibleStates(currentState, TypeConverter.A);
		bStates = EnglishNumberTransitionTable.getPossibleStates(currentState, TypeConverter.B);
		cStates = EnglishNumberTransitionTable.getPossibleStates(currentState, TypeConverter.C);
		dStates = EnglishNumberTransitionTable.getPossibleStates(currentState, TypeConverter.D);
		eStates = EnglishNumberTransitionTable.getPossibleStates(currentState, TypeConverter.E);
	}
	
	public void assertContains(Optional<List<Integer>> input, Integer... states) {
		for(Integer state : states) {
			assertTrue(input.get().contains(state));
		}
	}
	
	public <T> void assertEmptyStates(Optional<T>... emptyStates) {
		for(Optional<T> emptyState : emptyStates) {
			assertFalse(emptyState.isPresent());
		}
	}
	
	public <T> void assertFullStates(Optional<T>... fullStates) {
		for(Optional<T> fullState : fullStates) {
			assertTrue(fullState.isPresent());
		}
	}
}
