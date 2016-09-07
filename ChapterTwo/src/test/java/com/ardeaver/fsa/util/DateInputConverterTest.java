package com.ardeaver.fsa.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DateInputConverterTest {
	@Test
	public void testCorrectInputs() {
		assertEquals(DateInputConverter.A.intValue(), DateInputConverter.getConverteredInput("Christmas"));
		assertEquals(DateInputConverter.B.intValue(), DateInputConverter.getConverteredInput("August"));
		assertEquals(DateInputConverter.C.intValue(), DateInputConverter.getConverteredInput("June"));
		assertEquals(DateInputConverter.D.intValue(), DateInputConverter.getConverteredInput("February"));
		assertEquals(DateInputConverter.H.intValue(), DateInputConverter.getConverteredInput("the"));
		assertEquals(DateInputConverter.I.intValue(), DateInputConverter.getConverteredInput("of"));
		assertEquals(DateInputConverter.J.intValue(), DateInputConverter.getConverteredInput("Andrew"));
	}
	
	@Test
	public void testCorrectDates() {
		assertEquals(DateInputConverter.E.intValue(), DateInputConverter.getConvertedNumInputForState(1, 31));
		assertEquals(DateInputConverter.J.intValue(), DateInputConverter.getConvertedNumInputForState(2, 31));
		assertEquals(DateInputConverter.E.intValue(), DateInputConverter.getConvertedNumInputForState(1, 30));
		assertEquals(DateInputConverter.F.intValue(), DateInputConverter.getConvertedNumInputForState(2, 30));
		assertEquals(DateInputConverter.G.intValue(), DateInputConverter.getConvertedNumInputForState(3, 29));
		
		assertEquals(DateInputConverter.E.intValue(), DateInputConverter.getConvertedNumInputForState(4, 31));
		assertEquals(DateInputConverter.F.intValue(), DateInputConverter.getConvertedNumInputForState(4, 30));
		assertEquals(DateInputConverter.G.intValue(), DateInputConverter.getConvertedNumInputForState(4, 29));
	}
}
