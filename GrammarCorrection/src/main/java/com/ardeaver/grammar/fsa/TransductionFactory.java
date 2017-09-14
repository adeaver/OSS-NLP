package com.ardeaver.grammar.fsa;

/**
 * The TransductionFactory class is used by FSTs to
 * generate new transductions
 * 
 * @author Andrew Deaver
 * @version 1.0
 * @since 2017-02-06
 */
public abstract class TransductionFactory {
	/**
	 * Returns an instance of a new transduction
	 * 
	 * @return new instance of a Transduction
	 */
	public abstract Transduction getTransduction();
}
