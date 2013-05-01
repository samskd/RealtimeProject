package edu.nyu.trendingtopics.storm.bolts;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

/**
 * Prints the tuple to standard output.
 * 
 * @author samitpatel
 * */
public class PrinterBolt extends BaseBasicBolt {

    private static final long serialVersionUID = 8666453132062210863L;

    /**
     * Prints the tuple to standard output.
     * 
     * @param tuple Tuple
     * @param collector Collector
	 * */
	@Override
    public void execute(Tuple tuple, BasicOutputCollector collector) {
        System.out.println(tuple);
    }
	
	/**
	 * Declares the output fields of this bolt.
	 * 
	 * @param declarer {@link OutputFieldsDeclarer}
	 * */
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {}
    
}

