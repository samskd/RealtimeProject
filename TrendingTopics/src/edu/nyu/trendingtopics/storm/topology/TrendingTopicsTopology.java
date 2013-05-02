package edu.nyu.trendingtopics.storm.topology;

import backtype.storm.generated.StormTopology;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import edu.nyu.trendingtopics.storm.bolts.FilterNoiseWords;
import edu.nyu.trendingtopics.storm.bolts.TokenExtractor;
import edu.nyu.trendingtopics.storm.bolts.WordCounter;
import edu.nyu.trendingtopics.storm.bolts.WriteToCassandra;
import edu.nyu.trendingtopics.storm.spouts.TwitterSpout;

/**
 * Creates the storm topology for Trending Topics. Topology consists of in order, {@link TwitterSpout} spout, 
 * {@link TokenExtractor}, {@link FilterNoiseWords}, {@link WordCounter} and {@link WriteToCassandra} bolts.
 * 
 * @author samitpatel
 */
public class TrendingTopicsTopology {

	/**
	 * Returns the Trending Topics topology.
	 * 
	 * @return Trending Topics topology
	 * */
	public static StormTopology getTopology() {

		TopologyBuilder builder = new TopologyBuilder();

		builder.setSpout("spout", new TwitterSpout());
		
		builder.setBolt("tokenExtractor", new TokenExtractor())
		.shuffleGrouping("spout");
		
		builder.setBolt("noiseFilter", new FilterNoiseWords())
		.shuffleGrouping("tokenExtractor");
		
		builder.setBolt("wordCounter", new WordCounter(), 1)
		.fieldsGrouping("noiseFilter", new Fields("filteredToken")); 
		
		builder.setBolt("cassandraWriter", new WriteToCassandra(), 3)
		.shuffleGrouping("wordCounter");

		return builder.createTopology();
	}
}
