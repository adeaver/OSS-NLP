package com.ardeaver.grammar.dto;

public class SubjectVerbAgreementDto {
	private String partOfSpeech;
	private int count;
	
	public SubjectVerbAgreementDto() {}
	
	public SubjectVerbAgreementDto(String partOfSpeech, int count) {
		this.partOfSpeech = partOfSpeech;
		this.count = count;
	}

	public String getPartOfSpeech() {
		return partOfSpeech;
	}

	public void setPartOfSpeech(String partOfSpeech) {
		this.partOfSpeech = partOfSpeech;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}
