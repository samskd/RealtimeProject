package edu.nyu.storm.bolts;

import java.util.List;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import edu.nyu.util.Stemmer;

public class TokenStemmer extends BaseBasicBolt {

	private static final long serialVersionUID = 5168687340899971753L;

	@Override
	public void execute(Tuple tweetTokens, BasicOutputCollector collector) {
		
		@SuppressWarnings("unchecked")
		List<Object> tokens = (List<Object>) tweetTokens.getValueByField("tweetTokens");
		
		for(int i=0;i<tokens.size(); i++){
			String token = (String) tokens.get(i);
			tokens.set(i, Stemmer.stem(token));
		}
		
		collector.emit(tokens);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("stemmedTokens"));
	}

}
