package edu.nyu.util;



public class Stemmer {

	private static PorterStemmer stemmer = new PorterStemmer();
	
	public static String stem(String word){
		return stemmer.stem(word);
	}
}
