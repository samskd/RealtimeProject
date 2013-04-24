package edu.nyu.storm.bolts;


import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;

import edu.nyu.util.Tokenizer;

import twitter4j.Status;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

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

//		List<String> result = new ArrayList<String>();
//		analyzer = new EnglishAnalyzer(Version.LUCENE_42, StopWords.getStopWordSet());
//
//		try {
//			TokenStream stream  = analyzer.tokenStream(null, new StringReader(tweetText));
//			TokenStreamComponents createComponents = analyzer.createComponents(null, new StringReader(tweetText));
//			
//			try{
//			System.out.println(stream.incrementToken());
//			}catch(Exception e){
//				System.err.println("Erro");
//			}
//			while(stream.incrementToken()) {
//				String term = stream.getAttribute(CharTermAttribute.class).toString();
//				System.out.print(term+", ");
//				result.add(term);
//			}
//			
//		}
//		catch(IOException e) {
//			// not thrown b/c we're using a string reader...
//			throw new RuntimeException(e);
//		}
//
//		System.out.println("\n\n\n");
//		return result;
    	return Tokenizer.tokenize(tweetText);
	}  

}