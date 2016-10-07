package com.ardeaver.postagging.emission;

import com.ardeaver.postagging.dao.EmissionDao;

public class EmissionCalculator {
	private EmissionDao emissionDao;
	
	public EmissionCalculator() {
		emissionDao = new EmissionDao();
	}
	
	public double getEmissionProbability(String word, String state) {
		int emissionsForPos = emissionDao.getEmissionsForPOS(word, state);
		int totalEmissions = emissionDao.getTotalEmissionsForWord(word);
		
		if(totalEmissions != 0) {
			return ((double) emissionsForPos) / totalEmissions;
		}
		
		return 1;
	}
}
