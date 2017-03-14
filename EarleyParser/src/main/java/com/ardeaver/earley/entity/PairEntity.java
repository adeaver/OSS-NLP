package com.ardeaver.earley.entity;

import java.util.ArrayList;
import java.util.List;

public class PairEntity implements Entity, Cloneable {
	private SingleEntity head;
	private List<Entity> children;
	private int star, startIndex, endIndex;
	private String representation;
	
	public PairEntity(SingleEntity head, List<Entity> children, int star, int startIndex, int endIndex) {
		this.head = head;
		this.children = children;
		this.star = star;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.representation = null;
	}
	
	public PairEntity(PairEntity copy) {
		this.head = copy.head;
		this.children = copy.children;
		this.star = copy.star;
		this.startIndex = copy.startIndex;
		this.endIndex = copy.endIndex;
		this.representation = null;
	}
	
	public String getHead() {
		return head.getEntity();
	}
	
	public List<Entity> getChildren() {
		return children;
	}
	
	public int getStar() {
		return star;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}
	
	public Entity getNext() {
		return star < children.size() ? children.get(star) : null;
	}
	
	public void advanceStar(Entity newEntity) {
		if(newEntity instanceof PairEntity) {
			children.set(star, newEntity);
			star++;
			endIndex = ((PairEntity) newEntity).endIndex;
		}
	}
	
	public boolean isComplete() {
		return star == children.size();
	}

	@Override
	public String toString() {
		String child = "( " + head.toString() + " ";
		
		for(int i = 0; i < children.size(); i++) {
			child += children.get(i).toString() + " ";
		}
		
		child += ")";
		
		return child;
	}
	
	public String getEqualsString() {
		if(representation == null) {
			representation = head.getEntity() + " -> ";
			
			for(int i = 0; i < children.size(); i++) {
				representation += children.get(i).getHead() + " ";
			}
			
			representation = representation.trim();
			
			return representation;
		}
		
		return representation;
	}
	
	@Override
	public PairEntity clone() {
		List<Entity> childrenCopy = new ArrayList<Entity>(this.children);
		return new PairEntity(this.head, childrenCopy, star, startIndex, endIndex);
	}
	
	@Override
	public boolean equals(Object arg0) {
		if(arg0 instanceof PairEntity) {
			PairEntity arg = (PairEntity) arg0;
			
			if(arg.getEqualsString().equals(this.getEqualsString())) {
				return true;
			}
		}
		
		return false;
	}
}
