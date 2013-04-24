package edu.nyu.util;

import org.tartarus.snowball.ext.PorterStemmer;

/**
 * Stemmer based on Porter stemming algorithm.
 * @author samitpatel
 * */
public class Stemmer {

	private static PorterStemmer stemmer = new PorterStemmer();
	
	/**
	 * Stems the word using Porter Stemming algorithm
	 * @param word Word to be stemmed.
	 * @return Stemmed word
	 * */
	public static String stem(String word){
		stemmer.setCurrent(word);
		stemmer.stem();
		String stemmedValue = stemmer.getCurrent();
		
		return stemmedValue;
	}
}
