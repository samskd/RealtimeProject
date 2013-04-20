package edu.nyu.util;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

public class Tokenizer {

	private static Analyzer analyzer  = new StopAnalyzer(Version.LUCENE_42, StopWords.getStopWordSet());

	public static List<String> tokenize(String text){
		List<String> result = new ArrayList<String>();
		try{

			TokenStream stream  = analyzer.tokenStream(null, new StringReader(text));

			while(stream.incrementToken()) {
				String term = stream.getAttribute(CharTermAttribute.class).toString();
				result.add(term);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

}
