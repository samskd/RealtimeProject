package edu.nyu.util;

import org.tartarus.snowball.ext.PorterStemmer;

public class Stemmer {

	private static PorterStemmer stemmer = new PorterStemmer();
	
	public static String stem(String word){
		stemmer.setCurrent(word);
		stemmer.stem();
		String stemmedValue = stemmer.getCurrent();
		
		return stemmedValue;
	}
}
