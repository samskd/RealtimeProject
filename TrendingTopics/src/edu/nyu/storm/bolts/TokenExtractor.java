package edu.nyu.storm.bolts;


import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

import twitter4j.Status;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import edu.nyu.util.StopWords;

public class TokenExtractor extends BaseBasicBolt {

	private static final long serialVersionUID = 8812885839655720870L;
	private Analyzer analyzer;
	
	@Override
    public void execute(Tuple tuple, BasicOutputCollector collector) {
		Status tweet = (Status) tuple.getValueByField("tweet");
		
		List<String> tweets = parseKeywords(tweet.getText());
			
		List<Object> outputTuple = new ArrayList<Object>();
		outputTuple.add(tweets);
		
		collector.emit(outputTuple);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer ofd) {
    	ofd.declare(new Fields("tweetTokens"));
    }
    
    
    private List<String> parseKeywords(String tweetText) {

		List<String> result = new ArrayList<String>();
		analyzer = new StopAnalyzer(Version.LUCENE_36, StopWords.getStopWordSet());
		
		TokenStream stream  = analyzer.tokenStream(null, new StringReader(tweetText));

		try {
			while(stream.incrementToken()) {
				result.add(stream.getAttribute(CharTermAttribute.class).toString());
			}
		}
		catch(IOException e) {
			// not thrown b/c we're using a string reader...
		}

		return result;
	}  

}