package com.ardeaver.earley.dto;

import java.util.List;

public class Prediction implements Comparable<Prediction> {
	private String root;
	private List<String> children;
	private int count;
	
	public Prediction(String root, List<String> children, int count) {
		this.root = root;
		this.children = children;
		this.count = count;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public List<String> getChildren() {
		return children;
	}

	public void setChildren(List<String> children) {
		this.children = children;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public boolean equals(Object arg0) {
		if(arg0 instanceof Prediction) {
			Prediction p = (Prediction) arg0;
			return p.getRoot().equals(this.getRoot()) && p.getChildren().equals(this.getChildren());
		}
		
		return false;
	}

	public int compareTo(Prediction arg0) {
		if(arg0.getRoot().equals(this.getRoot()) && arg0.getChildren().equals(this.getChildren())) {
			return 0;
		}
		
		return 1;
	}
}
