package com.ardeaver.postagging.dto;

public class TransitionDto {
	private String fromGram, toGram;
	private int count;
	
	public TransitionDto(String from, String to, int c) {
		fromGram = from;
		toGram = to;
		count = c;
	}

	public String getFromGram() {
		return fromGram;
	}

	public void setFromGram(String fromGram) {
		this.fromGram = fromGram;
	}

	public String getToGram() {
		return toGram;
	}

	public void setToGram(String toGram) {
		this.toGram = toGram;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
