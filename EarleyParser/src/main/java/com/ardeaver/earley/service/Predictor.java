package com.ardeaver.earley.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ardeaver.earley.dao.ParsingDao;
import com.ardeaver.earley.dto.Prediction;
import com.ardeaver.earley.entity.Entity;
import com.ardeaver.earley.entity.PairEntity;
import com.ardeaver.earley.entity.SingleEntity;

public class Predictor {
	private ParsingDao parsingDao;
	private HashMap<String, List<Prediction>> parses;
	
	public Predictor() {
		parsingDao = new ParsingDao();
		parses = new HashMap<String, List<Prediction>>();
		initialize();
	}
	
	public List<PairEntity> predict(PairEntity p) {
		List<PairEntity> pairs = new ArrayList<PairEntity>();
		
		List<Prediction> ps = parses.get(p.getNext().getHead());
		
		if(ps != null) {
			for(Prediction predict : ps) {
				pairs.add(convertToPairEntity(predict, p));
			}
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
	
	private void initialize() {
		List<Prediction> predictions = parsingDao.getAllPredictions();
		
		for(Prediction p : predictions) {
			if(!parses.containsKey(p.getRoot())) {
				parses.put(p.getRoot(), new ArrayList<Prediction>());
			} 
			
			parses.get(p.getRoot()).add(p);
		}
	}
}
