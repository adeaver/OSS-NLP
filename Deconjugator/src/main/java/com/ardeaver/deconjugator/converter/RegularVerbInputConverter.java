package com.ardeaver.deconjugator.converter;

public class RegularVerbInputConverter {
	public static final Integer ALL = 0;
	public static final Integer A = 1;
	public static final Integer O = 2;
	public static final Integer E = 3;
	public static final Integer I = 4;
	public static final Integer N = 5;
	public static final Integer M = 6;
	public static final Integer S = 7;
	public static final Integer END = 8;
	
	public static Integer processToken(String inputToken) {
		if (inputToken.equals("a")) {
			return A;
		} else if (inputToken.equals("o")) {
			return O;
		} else if (inputToken.equals("e")) {
			return E;
		} else if (inputToken.equals("i")) {
			return I;
		} else if (inputToken.equals("n")) {
			return N;
		} else if (inputToken.equals("m")) {
			return M;
		} else if (inputToken.equals("s")) {
			return S;
		} else if (inputToken.equals("#")) {
			return END;
		}
		
		return ALL;
	}
}
