package com.ardeaver.fsa.util;

import java.util.Arrays;
import java.util.List;

public class DateInputConverter {
	private static final List<String> HOLIDAYS = Arrays.asList("Christmas", "New Year's Eve", "Thanksgiving", 
												"Memorial Day", "Labor Day");
	
	private static final List<String> MONTHS_31 = Arrays.asList("January", "March", "May", "July", 
											   "August", "October", "December");
	
	private static final List<String> MONTHS_30 = Arrays.asList("April", "June", "September", "November");
	
	private static final String FEBRUARY = "February";
	
	private static final String THE = "the";
	
	private static final String OF = "of";
	
	
	public static final Integer A = 0;
	public static final Integer B = 1;
	public static final Integer C = 2;
	public static final Integer D = 3;
	public static final Integer E = 4;
	public static final Integer F = 5;
	public static final Integer G = 6;
	public static final Integer H = 7;
	public static final Integer I = 8;
	public static final Integer J = 9;
	
	public static int getConverteredInput(String input) {
		if(HOLIDAYS.contains(input)) {
			return A;
		} else if(MONTHS_31.contains(input)) {
			return B;
		} else if(MONTHS_30.contains(input)) {
			return C;
		} else if (FEBRUARY.equals(input)) {
			return D;
		} else if (THE.equals(input)) {
			return H;
		} else if(OF.equals(input)) {
			return I;
		}
		
		return J;
	}
	
	public static int getConvertedNumInputForState(int state, int date) {
		if((state != 1 && state != 2 && state != 3 && state != 4) || (date < 1 || date > 31)) {
			return J;
		}
		
		if(state != 4) {
			if(state == 1) {
				return E;
			} else if(state == 2) {
				return date <= 30 ? F : J;
			} else if(state == 3) {
				return date <= 29 ? G : J;
			}
		} else {
			if(date == 31) {
				return E;
			} else if (date == 30) {
				return F;
			} else {
				return G;
			}
		}
		
		return J;
	}
}
