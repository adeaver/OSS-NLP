package com.ardeaver.earley.parser;

import java.util.ArrayList;
import java.util.List;

import com.ardeaver.earley.dao.ParsingDao;
import com.ardeaver.earley.dao.PartOfSpeechDao;
import com.ardeaver.earley.dto.Parse;
import com.ardeaver.earley.dto.Prediction;

public class EarleyParser {
	private List<String> terminals;
	private PartOfSpeechDao partOfSpeechDao;
	private ParsingDao parsingDao;
	
	public EarleyParser() {
		partOfSpeechDao = new PartOfSpeechDao();
		parsingDao = new ParsingDao();
		terminals = partOfSpeechDao.getPartsOfSpeech();
	}
	
	public Parse parseSentence(String input) {
		List<List<Prediction>> chart = new ArrayList<List<Prediction>>();
		List<String> tokens = tokenize(input);
		
		String root = "S";
		List<Prediction> predictions = predict(root, 0, 0);
		
		Prediction prediction, np;
		
		chart.add(new ArrayList<Prediction>());
		chart.get(0).addAll(predictions);
		
		for(int i = 0; i < tokens.size(); i++) {
			chart.add(new ArrayList<Prediction>());
			
			for(int j = 0; j < chart.get(i).size(); j++) {
				prediction = chart.get(i).get(j);
				if(prediction.getChildren().size() <= prediction.getPointerIndex()) {
					List<Prediction> p = complete(chart.get(prediction.getStartIndex()), prediction);
					
					for(Prediction p1 : p) {
						if(!chart.get(i).contains(p1)) {
							chart.get(i).add(p1);
						}
					}
				} else {
					if(terminals.contains(prediction.getChildren().get(prediction.getPointerIndex()))) {
						if(scan(tokens.get(i), prediction.getChildren().get(prediction.getPointerIndex()))) {
							np = new Prediction(prediction.getRoot(), prediction.getChildren(), prediction.getCount(), prediction.getStartIndex(), prediction.getEndIndex()+1);
							np.setPointerIndex(np.getPointerIndex()+1);
							chart.get(i+1).add(np);
						}
					} else {
						predictions = predict(prediction.getChildren().get(prediction.getPointerIndex()), prediction.getStartIndex(), prediction.getEndIndex());
						
						for(Prediction p : predictions) {
							if(!chart.get(i).contains(p)) {
								chart.get(i).add(p);
							}
						}
					}
				}
			}
		}
		
		System.out.println("End Size: " + chart.get(chart.size()-1).size());
		System.out.println("End: " + chart.get(chart.size()-1));
		
		return null;
	}

	private List<Prediction> predict(String root, int beginIndex, int endIndex) {
		return parsingDao.getPredictionsForRoot(root, beginIndex, endIndex);
	}
	
	private boolean scan(String word, String terminal) {
		return partOfSpeechDao.getPartOfSpeechForWord(word).contains(terminal);
	}
	
	private List<Prediction> complete(List<Prediction> predictions, Prediction p) {
		List<Prediction> newPredictions = new ArrayList<Prediction>();
		Prediction np;
		
		for(Prediction p1 : predictions) {
			if(p1.getChildren().size() > p1.getEndIndex() && p1.getChildren().get(p1.getPointerIndex()).equals(p.getRoot())) {
				np = new Prediction(p1.getRoot(), p1.getChildren(), p1.getCount(), p1.getStartIndex(), p.getEndIndex());
				np.setPointerIndex(p1.getPointerIndex()+1);
				newPredictions.add(np);
			}
		}
		
		return newPredictions;
	}
	
	private List<String> tokenize(String input) {
		List<String> list = new ArrayList<String>();
		String[] tokens = input.split("\\s+");
		
		for(String t : tokens) {
			list.add(t);
		}
		
		return list;
	}
}
