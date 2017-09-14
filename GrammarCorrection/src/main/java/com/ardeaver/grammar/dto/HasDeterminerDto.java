package com.ardeaver.grammar.dto;

public class HasDeterminerDto extends DeterminerDto {
	private String determiner;
	
	public HasDeterminerDto() {}
	
	public HasDeterminerDto(int count, String word, String determiner) {
		super(count, word);
		this.determiner = determiner;
	}

	public String getDeterminer() {
		return determiner;
	}

	public void setDeterminer(String determiner) {
		this.determiner = determiner;
	}
}
