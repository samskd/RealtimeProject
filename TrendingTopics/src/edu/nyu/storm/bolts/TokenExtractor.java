package edu.nyu.storm.bolts;


import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import edu.nyu.util.Tokenizer;

/**
 * Storm bolt that tokenizes the text of tweet into tokens. 
 * @author samitpatel
 * */
public class TokenExtractor extends BaseBasicBolt {

	private static final long serialVersionUID = 8812885839655720870L;
	
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
    	return Tokenizer.tokenize(tweetText);
	}  

}