package com.ardeaver.earley.dto;

import java.util.List;

public class Prediction {
	private String root;
	private List<String> children;
	private int count;
	private int startIndex, endIndex;
	
	public Prediction(String root, List<String> children, int count) {
		this.root = root;
		this.children = children;
		this.count = count;
	}
	
	public Prediction(String root, List<String> children, int count, int startIndex, int endIndex) {
		this(root, children, count);
		this.startIndex = startIndex;
		this.endIndex = endIndex;
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
	
}
