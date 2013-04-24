package edu.nyu.storm.bolts;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import edu.nyu.util.NoiseWords;

public class ExtractNoiseWords extends BaseBasicBolt{

	private static final long serialVersionUID = 5078004842335973173L;

	@Override
	public void declareOutputFields(OutputFieldsDeclarer ofd) {
		ofd.declare(new Fields("filteredTokens"));
	}

	@Override
	public void execute(Tuple tuple, BasicOutputCollector collector) {

		String tweetToken = (String) tuple.getValueByField("tweetToken");
			
		if(!NoiseWords.isNoiseWord(tweetToken)) 
			collector.emit(new Values(tweetToken));
	}

}
