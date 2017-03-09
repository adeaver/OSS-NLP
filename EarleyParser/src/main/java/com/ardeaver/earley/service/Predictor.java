package com.ardeaver.earley.service;

import java.util.ArrayList;
import java.util.List;

import com.ardeaver.earley.dao.ParsingDao;
import com.ardeaver.earley.dto.Prediction;
import com.ardeaver.earley.entity.Entity;
import com.ardeaver.earley.entity.PairEntity;
import com.ardeaver.earley.entity.SingleEntity;

public class Predictor {
	private ParsingDao parsingDao;
	
	public Predictor() {
		parsingDao = new ParsingDao();
	}
	
	public List<PairEntity> predict(PairEntity p) {
		List<PairEntity> pairs = new ArrayList<PairEntity>();
		
		List<Prediction> ps = parsingDao.getPredictionsForRoot(p.getNext().getHead());
		
		for(Prediction predict : ps) {
			pairs.add(convertToPairEntity(predict, p));
		}
		
		return pairs;
	}
	
	public List<PairEntity> getInitialList() {
		List<PairEntity> pairs = new ArrayList<PairEntity>();
		
		List<Prediction> ps = parsingDao.getPredictionsForRoot("S");
		
		for(Prediction predict : ps) {
			pairs.add(convertToPairEntity(predict, 0, 0));
		}
		
		return pairs;
	}
	
	private PairEntity convertToPairEntity(Prediction p, int start, int end) {
		SingleEntity head = new SingleEntity(p.getRoot());
		List<Entity> children = new ArrayList<Entity>();
		
		for(String child : p.getChildren()) {
			children.add(new SingleEntity(child));
		}
		
		return new PairEntity(head, children, 0, start, end);
	}
	
	private PairEntity convertToPairEntity(Prediction p, PairEntity startEntity) {
		return convertToPairEntity(p, startEntity.getEndIndex(), startEntity.getEndIndex());
	}
}
