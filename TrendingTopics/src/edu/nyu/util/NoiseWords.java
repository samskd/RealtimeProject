package edu.nyu.util;


/**
 * This class checks if the word is categorized as a noise word. Helps to remove the unwanted words.
 * 
 * @author samitpatel
 * */
public class NoiseWords {

	private static String noiseRegex = "(\\d+|\\?+)";
	
	/**
	 * Checks if the word is noise
	 * @param word Word to be checked.
	 * @return Returns <code>True</code> if the word is noise, otherwise <code>False</code>.
	 * */
	public static synchronized boolean isNoiseWord(String word){
		return word.matches(noiseRegex);
	}
	
}
