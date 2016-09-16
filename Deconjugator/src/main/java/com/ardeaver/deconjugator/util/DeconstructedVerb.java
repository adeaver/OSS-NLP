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
}
