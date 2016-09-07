package com.ardeaver.fsas;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class NumberFSATest {
	private NumberFSA fsa;
	
	@Before
	public void setup() {
		fsa = new NumberFSA();
	}
	
	@Test
	public void testBasicNumbers() {		
		assertTrue(fsa.isValid("one"));
		assertTrue(fsa.isValid("two"));
		assertTrue(fsa.isValid("ten"));
		assertTrue(fsa.isValid("twenty"));
		
		assertFalse(fsa.isValid("Andrew"));
		assertFalse(fsa.isValid("yellow"));
	}
	
	@Test
	public void testTwoPartNumbers() {
		assertTrue(fsa.isValid("one hundred"));
		assertTrue(fsa.isValid("twenty-one"));
		assertTrue(fsa.isValid("ten thousand"));
		
		assertFalse(fsa.isValid("one andrew"));
		assertFalse(fsa.isValid("ten andrew"));
	}
	
	@Test
	public void testComplexNumbers() {
		assertTrue(fsa.isValid("one hundred thousand"));
		assertTrue(fsa.isValid("one hundred thirty-three thousand and twenty-two"));
		assertTrue(fsa.isValid("three hundred forty-four thousand nine hundred and thirty-six"));
		assertTrue(fsa.isValid("eleven thousand nine hundred and twelve"));
		assertTrue(fsa.isValid("twenty-one thousand forty-five"));
		
		assertFalse(fsa.isValid("one hundred forty-four thousand twelve seven nine"));
		assertFalse(fsa.isValid("hundred fifty seven twelve"));
		assertFalse(fsa.isValid("one two three four"));
	}
}
