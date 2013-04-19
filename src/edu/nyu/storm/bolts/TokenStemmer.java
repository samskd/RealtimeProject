package edu.nyu.storm.bolts;

import java.util.ArrayList;
import java.util.List;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

public class PorterStemmer extends BaseBasicBolt {

	private static final long serialVersionUID = 5168687340899971753L;

	@Override
	public void execute(Tuple tweetTokens, BasicOutputCollector collector) {
		List<String> tokens = (List<String>) tweetTokens.getValueByField("tweetTokens");
		List<String> stemmedTokens = new ArrayList<String>();
		
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("stemTokens"));
	}

}
