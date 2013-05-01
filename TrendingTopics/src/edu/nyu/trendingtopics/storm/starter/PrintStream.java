package edu.nyu.trendingtopics.storm.starter;


import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import edu.nyu.trendingtopics.storm.bolts.FilterNoiseWords;
import edu.nyu.trendingtopics.storm.bolts.TokenExtractor;
import edu.nyu.trendingtopics.storm.bolts.WordCounter;
import edu.nyu.trendingtopics.storm.bolts.WriteToCassandra;
import edu.nyu.trendingtopics.storm.spouts.TwitterSpout;

public class PrintStream {        

	public static void main(String[] args) {
    	TopologyBuilder builder = new TopologyBuilder();
        
        builder.setSpout("spout", new TwitterSpout());
        builder.setBolt("tokenExtractor", new TokenExtractor())
        	.shuffleGrouping("spout");
        builder.setBolt("noiseFilter", new FilterNoiseWords())
    		.shuffleGrouping("tokenExtractor");
        builder.setBolt("wordCounter", new WordCounter(), 1)
			.fieldsGrouping("noiseFilter", new Fields("filteredToken")); 
        builder.setBolt("cassandraWriter", new WriteToCassandra(), 5)
			.shuffleGrouping("wordCounter");
        
//        builder.setBolt("print", new PrinterBolt())
 //   	.shuffleGrouping("noiseFilter");
	
        
        Config conf = new Config();
        
        LocalCluster cluster = new LocalCluster();
        
        cluster.submitTopology("tredingTopics", conf, builder.createTopology());
       
  
    /*    Utils.sleep(10000);
        cluster.shutdown();*/
    }
}