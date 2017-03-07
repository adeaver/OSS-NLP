package com.ardeaver.earley.parser;

import org.junit.Test;

public class EarleyParserTest {
	private EarleyParser parser;
	
	public EarleyParserTest() {
		parser = new EarleyParser();
	}
	
	@Test
	public void testBasic() {
		String input = "Book that flight";
		
		parser.parseSentence(input);
	}
}
