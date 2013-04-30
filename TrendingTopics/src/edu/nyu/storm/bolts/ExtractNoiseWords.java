package edu.nyu.storm.bolts;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class ExtractNoiseWords extends BaseBasicBolt{

	private static final long serialVersionUID = 5078004842335973173L;
	private static String noiseRegex = "(\\W+|\\d+)";
	private static String englishNaiveRegex = "^[a-zA-Z#]+$"; 
	
	@Override
	public void execute(Tuple tuple, BasicOutputCollector collector) {

		String tweetToken = tuple.getStringByField("tweetToken");
		
		if(tweetToken.length() > 2 && 
				tweetToken.length() < 15 &&
				!tweetToken.matches(noiseRegex) && 
				tweetToken.matches(englishNaiveRegex))
			collector.emit(new Values(tweetToken));
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer ofd) {
		ofd.declare(new Fields("filteredToken"));
	}

}
