package com.ardeaver.earley.dto;

import java.util.List;

public class Prediction implements Comparable<Prediction> {
	private String root;
	private List<String> children;
	private int count;
	private int startIndex, endIndex, pointerIndex;
	
	public Prediction(String root, List<String> children, int count) {
		this(root, children, count, 0, 0);
	}
	
	public Prediction(String root, List<String> children, int count, int startIndex, int endIndex) {
		this.root = root;
		this.children = children;
		this.count = count;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.pointerIndex = 0;
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

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	public int getPointerIndex() {
		return pointerIndex;
	}

	public void setPointerIndex(int pointerIndex) {
		this.pointerIndex = pointerIndex;
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
	
	@Override
	public String toString() {
		
		String childString = "";
		
		for(int i = 0; i <= this.getChildren().size(); i++) {
			if(i == this.getEndIndex()) {
				childString += "* ";
			}
			if(i < this.getChildren().size()) {
				childString += this.getChildren().get(i) + " ";
			}
		}
		
		return this.getRoot() + " -> " + childString.trim() + " [" + this.getStartIndex() + ", " + this.getEndIndex() + "]";
	}
}
