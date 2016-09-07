package com.ardeaver.transition;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ardeaver.fsa.util.DateInputConverter;

public class DateTransitionTableTest {
	@Test
	public void testZeroFullStates() {
		assertEquals(11, DateTransitionTable.getNextState(0, DateInputConverter.A).intValue());
		assertEquals(1, DateTransitionTable.getNextState(0, DateInputConverter.B).intValue());
		assertEquals(2, DateTransitionTable.getNextState(0, DateInputConverter.C).intValue());
		assertEquals(3, DateTransitionTable.getNextState(0, DateInputConverter.D).intValue());
		assertEquals(4, DateTransitionTable.getNextState(0, DateInputConverter.H).intValue());
	}
	
	@Test
	public void testOneFullState() {
		assertEquals(11, DateTransitionTable.getNextState(1, DateInputConverter.E).intValue());
	}
	
	@Test
	public void testTwoFullState() {
		assertEquals(11, DateTransitionTable.getNextState(2, DateInputConverter.F).intValue());
	}
	
	@Test
	public void testThreeFullState() {
		assertEquals(11, DateTransitionTable.getNextState(3, DateInputConverter.G).intValue());
	}
	
	@Test
	public void testFourFullStates() {
		assertEquals(5, DateTransitionTable.getNextState(4, DateInputConverter.E).intValue());
		assertEquals(6, DateTransitionTable.getNextState(4, DateInputConverter.F).intValue());
		assertEquals(7, DateTransitionTable.getNextState(4, DateInputConverter.G).intValue());
	}
	
	@Test
	public void testFiveFullState() {
		assertEquals(8, DateTransitionTable.getNextState(5, DateInputConverter.I).intValue());
	}
	
	@Test
	public void testSixFullState() {
		assertEquals(9, DateTransitionTable.getNextState(6, DateInputConverter.I).intValue());
	}
	
	@Test
	public void testSevenFullState() {
		assertEquals(10, DateTransitionTable.getNextState(7, DateInputConverter.I).intValue());
	}
	
	@Test
	public void testEightFullState() {
		assertEquals(11, DateTransitionTable.getNextState(8, DateInputConverter.B).intValue());
	}
	
	@Test
	public void testNineFullState() {
		assertEquals(11, DateTransitionTable.getNextState(9, DateInputConverter.B).intValue());
		assertEquals(11, DateTransitionTable.getNextState(9, DateInputConverter.C).intValue());
	}
	
	@Test
	public void testTenFullState() {
		assertEquals(11, DateTransitionTable.getNextState(10, DateInputConverter.B).intValue());
		assertEquals(11, DateTransitionTable.getNextState(10, DateInputConverter.C).intValue());
		assertEquals(11, DateTransitionTable.getNextState(10, DateInputConverter.D).intValue());
	}
}
