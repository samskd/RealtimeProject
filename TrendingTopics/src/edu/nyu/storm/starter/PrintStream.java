package edu.nyu.storm.starter;

// to use this example, uncomment the twitter4j dependency information in the project.clj,
// uncomment storm.starter.spout.TwitterSampleSpout, and uncomment this class


import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;
import edu.nyu.storm.bolts.TokenExtractor;
import edu.nyu.storm.bolts.PrinterBolt;
import edu.nyu.storm.bolts.WordCounter;
import edu.nyu.storm.bolts.WordNormalizer;
import edu.nyu.storm.spouts.TwitterSpout;
import backtype.storm.tuple.Fields;

public class PrintStream {        
    public static void main(String[] args) {
        
    	TopologyBuilder builder = new TopologyBuilder();
        
        builder.setSpout("spout", new TwitterSpout());
        builder.setBolt("tokenExtractor", new TokenExtractor())
        	.shuffleGrouping("spout");
//        builder.setBolt("tokenStemmer", new TokenExtractor())
//    		.shuffleGrouping("tokenExtractor");
        builder.setBolt("word-normalizer", new WordNormalizer())
			.shuffleGrouping("tokenExtractor");
        builder.setBolt("word-counter", new WordCounter(),1)
		.fieldsGrouping("word-normalizer", new Fields("word"));
    //    builder.setBolt("print", new PrinterBolt())
    //    	.shuffleGrouping("tokenExtractor");
                
        
        Config conf = new Config();
        
        LocalCluster cluster = new LocalCluster();
        
        cluster.submitTopology("test", conf, builder.createTopology());
        
        Utils.sleep(10000);
        cluster.shutdown();
    }
}
