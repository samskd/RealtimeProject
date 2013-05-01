package edu.nyu.trendingtopics.storm.starter;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import edu.nyu.trendingtopics.storm.topology.TrendingTopicsTopology;

/**
 * This class creates the storm topology for TrendingTopics and submit the topology
 * to the storm nimbus for execution.
 * 
 * @author samitpatel
 * */
public class Pilot {        

	public static void main(String[] args) {
        
        Config conf = new Config();
        conf.setMaxTaskParallelism(3);
        
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("tredingTopics", conf, TrendingTopicsTopology.getTopology());
    
        //this topology runs indefinitely.
        //cluster.shutdown();
    }
}