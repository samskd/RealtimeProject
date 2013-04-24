package edu.nyu.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.en.EnglishPossessiveFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.miscellaneous.KeywordMarkerFilter;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

/**
 * Tokenizes the text into token by applying few filter in the process. It filters the token
 * based on {@link StandardFilter}, {@link EnglishPossessiveFilter}, {@link LowerCaseFilter},
 * {@link StopFilter}, {@link KeywordMarkerFilter} and {@link PorterStemFilter}.
 * 
 * @author samitpatel
 * */
public class Tokenizer {

	//Analyzer used to build the token stream. and excluding the word in the stopword list.
	private static final Analyzer analyzer  = new EnglishAnalyzer(Version.LUCENE_42, StopWords.getStopWordSet());

	/**
	 * Tokenizes the text into token by applying the filters.
	 * @param text Text to tokenize.
	 * @return Returns the list of tokens.
	 * */
	public static List<String> tokenize(String text){
		List<String> result = new ArrayList<String>();
		TokenStream stream  = null;
		
		try{
			stream = analyzer.tokenStream("tweetcontent", new StringReader(text));
			stream.reset();
			
			CharTermAttribute termAttribute = stream.addAttribute(CharTermAttribute.class);
			while(stream.incrementToken()) {
				String term = termAttribute.toString();
				result.add(term);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
				try {
					if(stream != null) {
						stream.end();
						stream.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		return result;
	}

}
