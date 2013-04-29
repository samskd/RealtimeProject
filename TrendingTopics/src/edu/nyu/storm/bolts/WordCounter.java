package edu.nyu.storm.bolts;

import static edu.nyu.Constant.CL;
import static edu.nyu.Constant.UTF8;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.InvalidRequestException;
import org.apache.cassandra.thrift.TimedOutException;
import org.apache.cassandra.thrift.UnavailableException;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;

import edu.nyu.Connector;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

public class WordCounter extends BaseBasicBolt {
	private static final Logger LOG = Logger.getLogger(CopyOfWordCounter.class);
	private static Cassandra.Client client;
	private static Connector connector;
	
	Integer id;
	String name;
	Map<String, Integer> counters;

	/**
	 * At the end of the spout (when the cluster is shutdown
	 * We will show the word counters
	 */
	@Override
	public void cleanup() {
	/*	System.out.println("-- Word Counter ["+name+"-"+id+"] --");
		for(Map.Entry<String, Integer> entry : counters.entrySet()){
			System.out.println(entry.getKey()+": "+entry.getValue());
		}*/
	}

	/**
	 * On create 
	 */
	@Override
	public void prepare(Map stormConf, TopologyContext context) {
		this.counters = new HashMap<String, Integer>();
		this.name = context.getThisComponentId();
		this.id = context.getThisTaskId();
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {}


	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		if(counters.size()>1000){
			connector = new Connector();
			try {
				client = connector.connect();
				long timestamp = System.currentTimeMillis();
				ColumnParent parent = new ColumnParent("wordCount");
				for(Map.Entry<String, Integer> entry : counters.entrySet()){
					 Column idColumn;
					try {
						idColumn = new Column(toByteBuffer(entry.getKey()));
						idColumn.setValue(toByteBuffer(entry.getValue().toString()));
						idColumn.setTimestamp(timestamp);
						client.insert(toByteBuffer(timestamp+""), parent, idColumn, CL);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				counters.clear();
				System.out.println("clear");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
		String str = input.getString(0);
		/**
		 * If the word dosn't exist in the map we will create
		 * this, if not We will add 1 
		 */
		if(!counters.containsKey(str)){
			counters.put(str, 1);
		}else{
			Integer c = counters.get(str) + 1;
			counters.put(str, c);
		}
	}
	
	public static ByteBuffer toByteBuffer(String value) throws UnsupportedEncodingException{
		 return ByteBuffer.wrap(value.getBytes(UTF8));
	}
	
}
