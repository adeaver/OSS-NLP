package com.ardeaver.earley.entity;

import java.util.ArrayList;
import java.util.List;

public class PairEntity implements Entity, Cloneable {
	private SingleEntity head;
	private List<Entity> children;
	private int star, startIndex, endIndex;
	
	public PairEntity(SingleEntity head, List<Entity> children, int star, int startIndex, int endIndex) {
		this.head = head;
		this.children = children;
		this.star = star;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
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
	
	@Override
	public PairEntity clone() {
		List<Entity> childrenCopy = new ArrayList<Entity>(this.children);
		return new PairEntity(this.head, childrenCopy, star, startIndex, endIndex);
	}
	
	@Override
	public boolean equals(Object arg0) {
		if(arg0 instanceof PairEntity) {
			PairEntity arg = (PairEntity) arg0;
			
			if(arg.toString().equals(this.toString())) {
				return true;
			}
		}
		
		return false;
	}
}
