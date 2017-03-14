package com.ardeaver.earley.probabilistic.service;

import java.util.ArrayList;
import java.util.List;

import com.ardeaver.earley.dao.ParsingDao;
import com.ardeaver.earley.dto.ProbabilisiticPrediction;
import com.ardeaver.earley.entity.Entity;
import com.ardeaver.earley.entity.SingleEntity;
import com.ardeaver.earley.probabilistic.entity.ProbabilisticPairEntity;

public class ProbabilisiticPredictor {
	private ParsingDao parsingDao;
	
	public ProbabilisiticPredictor() {
		parsingDao = new ParsingDao();
	}
	
	public List<ProbabilisticPairEntity> predict(ProbabilisticPairEntity p) {
		List<ProbabilisticPairEntity> pairs = new ArrayList<ProbabilisticPairEntity>();
		
		List<ProbabilisiticPrediction> ps = parsingDao.getProbabilisiticPredictionsForRoot(p.getNext().getHead());
		
		if(ps != null) {
			for(ProbabilisiticPrediction predict : ps) {
				pairs.add(convertToPairEntity(predict, p));
			}
		}
		
		return pairs;
	}
	
	public List<ProbabilisticPairEntity> getInitialList() {
		List<ProbabilisticPairEntity> pairs = new ArrayList<ProbabilisticPairEntity>();
		
		List<ProbabilisiticPrediction> ps = parsingDao.getProbabilisiticPredictionsForRoot("S");
		
		for(ProbabilisiticPrediction predict : ps) {
			pairs.add(convertToPairEntity(predict, 0, 0));
		}
		
		return pairs;
	}
	
	private ProbabilisticPairEntity convertToPairEntity(ProbabilisiticPrediction p, int start, int end) {
		SingleEntity head = new SingleEntity(p.getRoot());
		List<Entity> children = new ArrayList<Entity>();
		
		for(String child : p.getChildren()) {
			children.add(new SingleEntity(child));
		}
		
		return new ProbabilisticPairEntity(head, children, 0, start, end, p.getProbability());
	}
	
	private ProbabilisticPairEntity convertToPairEntity(ProbabilisiticPrediction p, ProbabilisticPairEntity startEntity) {
		return convertToPairEntity(p, startEntity.getEndIndex(), startEntity.getEndIndex());
	}
}
