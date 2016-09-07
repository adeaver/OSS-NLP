package com.ardeaver.fsa.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TypeConverterTest {
	@Test
	public void isInGroupA() {
		assertEquals(TypeConverter.A, TypeConverter.findGroup("one"));
		assertEquals(TypeConverter.A, TypeConverter.findGroup("two"));
		assertEquals(TypeConverter.A, TypeConverter.findGroup("three"));
		
		assertFalse(TypeConverter.A.equals(TypeConverter.findGroup("ten")));
		assertFalse(TypeConverter.A.equals(TypeConverter.findGroup("twenty")));
	}
	
	@Test
	public void isInGroupB() {
		assertEquals(TypeConverter.B, TypeConverter.findGroup("ten"));
		assertEquals(TypeConverter.B, TypeConverter.findGroup("eleven"));
		assertEquals(TypeConverter.B, TypeConverter.findGroup("twelve"));
		
		assertFalse(TypeConverter.B.equals(TypeConverter.findGroup("one")));
		assertFalse(TypeConverter.B.equals(TypeConverter.findGroup("twenty")));
		assertFalse(TypeConverter.B.equals(TypeConverter.findGroup("hundred")));
	}
	
	@Test
	public void isInGroupC() {
		assertEquals(TypeConverter.C, TypeConverter.findGroup("twenty"));
		assertEquals(TypeConverter.C, TypeConverter.findGroup("thirty"));
		assertEquals(TypeConverter.C, TypeConverter.findGroup("ninety"));
		
		assertFalse(TypeConverter.C.equals(TypeConverter.findGroup("one")));
		assertFalse(TypeConverter.C.equals(TypeConverter.findGroup("ten")));
		assertFalse(TypeConverter.C.equals(TypeConverter.findGroup("thousand")));
	}
	
	@Test
	public void isInGroupD() {
		assertEquals(TypeConverter.D, TypeConverter.findGroup("hundred"));
		
		assertFalse(TypeConverter.D.equals(TypeConverter.findGroup("twenty")));
		assertFalse(TypeConverter.D.equals(TypeConverter.findGroup("thousand")));
	}
	
	@Test
	public void isInGroupE() {
		assertEquals(TypeConverter.E, TypeConverter.findGroup("thousand"));
		
		assertFalse(TypeConverter.E.equals(TypeConverter.findGroup("seven")));
		assertFalse(TypeConverter.E.equals(TypeConverter.findGroup("hundred")));
	}
}
