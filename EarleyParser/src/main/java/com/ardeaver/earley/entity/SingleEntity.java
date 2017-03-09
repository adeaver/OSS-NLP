package com.ardeaver.earley.entity;

public class SingleEntity implements Entity {
	private String entity;
	
	public SingleEntity(String entity) {
		this.entity = entity;
	}
	
	public String getHead() {
		return entity;
	}
	
	public String getEntity() {
		return entity;
	}
	
	public void setEntity(String entity) {
		this.entity = entity;
	}
	
	@Override
	public String toString() {
		return entity;
	}
	
	@Override
	public boolean equals(Object arg0) {
		if(arg0 instanceof SingleEntity) {
			if(((SingleEntity) arg0).entity.equals(entity)) {
				return true;
			}
		}
		
		return false;
	}
}
