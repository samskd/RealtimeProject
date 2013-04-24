package edu.nyu.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

public class Tokenizer {

	private static Analyzer analyzer  = new EnglishAnalyzer(Version.LUCENE_42, StopWords.getStopWordSet());

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
