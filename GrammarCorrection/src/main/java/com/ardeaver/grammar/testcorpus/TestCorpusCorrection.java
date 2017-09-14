package com.ardeaver.grammar.testcorpus;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.ardeaver.grammar.pipeline.Correction;
import com.ardeaver.grammar.pipeline.Corrector;

public class TestCorpusCorrection {
	private static final String WRITE_DIRECTORY = "/home/andrew/Projects/Alea_Sandbox/";
	
	private Corrector corrector;
	
	public TestCorpusCorrection() {
		corrector = new Corrector();
	}
	
	public void run() throws FileNotFoundException {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("test_corpus.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		PrintWriter outputWriter = new PrintWriter(WRITE_DIRECTORY + "correction_output.txt");
		
		String line = "";
		Correction output;
		List<String> corrections;
		
		while(line != null) {
			try {
				corrections = new ArrayList<String>();
				line = br.readLine();
				
				if(line != null) {
					try {
						output = corrector.correct(line);
						
						if(!corrections.contains(line.toLowerCase())) {
							outputWriter.println(line.toLowerCase() + " >> [" + output.toString() + "]");
						}
					} catch(Exception e) {
						outputWriter.println(line.toLowerCase() + " ERROR " + e.getMessage());
					}
				}
				
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			br.close();
			is.close();
			outputWriter.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	public static void main(String[] args) {
		TestCorpusCorrection testCorpus = new TestCorpusCorrection();
		try {
			testCorpus.run();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}*/
}
