package com.ardeaver.deconjugator.util;

public class DeconstructedVerb {
	private String root;
	private String[] endings;
	
	public DeconstructedVerb(String root, String[] endings) {
		this.root = root;
		this.endings = endings;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String[] getEndings() {
		return endings;
	}

	public void setEndings(String[] endings) {
		this.endings = endings;
	}
	
	public String[] reconstructVerb() {
		String[] output = new String[endings.length];
		
		for(int i = 0; i < endings.length; i++) {
			output[i] = root + applyEnding(endings[i]);
		}
		
		return output;
	}
	
	private static String applyEnding(String input) {
		if(input.equals("-AR")) {
			return "ar";
		} else if (input.equals("-IR")) {
			return "ir";
		}
		
		return "er";
	}
}
