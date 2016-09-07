package com.ardeaver.fsa.util;

import java.util.Arrays;
import java.util.List;

public class TypeConverter {
	public static final Integer A = 0;
	public static final Integer B = 1;
	public static final Integer C = 2;
	public static final Integer D = 3;
	public static final Integer E = 4;
	public static final Integer F = 5;
	
	private static final List<String> GROUP_A = Arrays.asList("one", "two", "three", "four", 
											 "five", "six", "seven", "eight", "nine");
	
	private static final List<String> GROUP_B = Arrays.asList("ten", "eleven", "twelve", "thirteen", "fourteen", 
											 "fifteen", "sixteen", "seventeen", "eighteen", "nineteen");
	
	private static final List<String> GROUP_C = Arrays.asList("twenty", "thirty", "forty", "fifty", 
												"sixty", "seventy", "eighty", "ninety");
	
	private static final List<String> GROUP_D = Arrays.asList("hundred");
	
	private static final List<String> GROUP_E = Arrays.asList("thousand");
	
	private TypeConverter() {}
	
	public static Integer findGroup(String input) {
		if(GROUP_A.contains(input)) {
			return A;
		} else if (GROUP_B.contains(input)) {
			return B;
		} else if (GROUP_C.contains(input)) {
			return C;
		} else if (GROUP_D.contains(input)) {
			return D;
		} else if (GROUP_E.contains(input)) {
			return E;
		} else {
			return F;
		}
	};
}
