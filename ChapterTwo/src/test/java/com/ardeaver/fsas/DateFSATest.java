package com.ardeaver.fsas;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class DateFSATest {
	DateFSA fsa;
	
	@Before
	public void setup() {
		fsa = new DateFSA();
	}
	
	@Test
	public void testValidDates() {
		assertTrue(fsa.isValid("the 22 of December"));
		assertTrue(fsa.isValid("March 15"));
		assertTrue(fsa.isValid("the 31 of July"));
	}
	
	@Test
	public void testInValidDates() {
		assertFalse(fsa.isValid("March 32"));
		assertFalse(fsa.isValid("Andrew Deaver"));
		assertFalse(fsa.isValid("Andrew 31"));
		assertFalse(fsa.isValid("a 31 of May"));
		assertFalse(fsa.isValid("February 30"));
	}
}
