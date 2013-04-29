package edu.nyu.util;

import java.io.FileReader;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.Version;


/**
 * This class holds the list of stopwords. It populates the words from the file.
 * @author samitpatel
 * */
public class StopWords {

	private static CharArraySet stopWordsCharArraySet = null;
	private static String stopWordsFile = "files/stopwords";
	private static boolean useLuceneStopWords = true;

	/**
	 * Checks whether the word is a stopword.
	 * 
	 * @param word Word to be checked.
	 * 
	 *@return Returns <code>True</code> if word is stopword, otherwise <code>False</code>.
	 * */
	public static boolean isStopWord(String word){
		if(stopWordsCharArraySet == null)
			populateStopWords();

		return stopWordsCharArraySet.contains(word);
	}

	/**
	 * Returns the set containing all the stopwords.
	 * 
	 * @return Set of stopwords.
	 * */
	public static CharArraySet getStopWordSet(){
		
		if(stopWordsCharArraySet == null)
			populateStopWords();

		return stopWordsCharArraySet;
	}


	/**
	 * Private method that lazily populates the stopwords from the file.
	 * */
	private static void populateStopWords(){

		if(useLuceneStopWords){
			stopWordsCharArraySet = StopAnalyzer.ENGLISH_STOP_WORDS_SET;
			return;
		}
		
		Set<String> stopWords = new HashSet<String>();

		Scanner scanner = null;
		FileReader fileReader = null;
		try{
			fileReader = new FileReader(stopWordsFile);
			scanner = new Scanner(fileReader);

			//populates the list of stopwords from the stopwords file.
			//ignores te empty line or the one that starts with '#'
			while(scanner.hasNextLine()){
				String word = scanner.nextLine();
				if(word.isEmpty() || word.startsWith("#")) continue;
				String[] words = word.trim().split("\\s+");
				for(String w : words)
					stopWords.add(w);
			}

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(scanner != null) scanner.close();
			try{
				if(fileReader != null) fileReader.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		stopWordsCharArraySet = new CharArraySet(Version.LUCENE_42, stopWords, true);
	}
}