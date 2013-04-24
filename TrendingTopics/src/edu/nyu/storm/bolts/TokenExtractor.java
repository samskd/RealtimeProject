package edu.nyu.storm.bolts;


import java.util.List;

import twitter4j.Status;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
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
		
		List<String> tweetTokens = parseKeywords(tweet.getText());
		for(String token : tweetTokens)	{
			collector.emit(new Values(token));
		}
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer ofd) {
    	ofd.declare(new Fields("tweetToken"));
    }
    
    private List<String> parseKeywords(String tweetText) {
    	return Tokenizer.tokenize(tweetText);
	}  

}