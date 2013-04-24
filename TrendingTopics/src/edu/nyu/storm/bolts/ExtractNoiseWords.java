package edu.nyu.storm.bolts;

import java.util.ArrayList;
import java.util.List;

import edu.nyu.util.NoiseWords;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

public class ExtractNoiseWords extends BaseBasicBolt{

	private static final long serialVersionUID = 5078004842335973173L;

	@Override
	public void declareOutputFields(OutputFieldsDeclarer ofd) {
		ofd.declare(new Fields("filteredTokens"));
	}

	@Override
	public void execute(Tuple tuple, BasicOutputCollector collector) {
		@SuppressWarnings("unchecked")
		List<String> tweetTokens = (List<String>) tuple.getValueByField("tweetTokens");
		
		List<String> filteredTokens = new ArrayList<String>();
		
		for(String token : tweetTokens){
			if(!NoiseWords.isNoiseWord(token))
				filteredTokens.add(token);
		}
			
		List<Object> outputTuple = new ArrayList<Object>();
		outputTuple.add(filteredTokens);
		collector.emit(outputTuple);
	}

}
