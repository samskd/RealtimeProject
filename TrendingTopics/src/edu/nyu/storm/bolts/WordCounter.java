package edu.nyu.storm.bolts;

import static edu.nyu.Constant.CL;
import static edu.nyu.Constant.UTF8;
import static edu.nyu.Constant.WORDS_WRITE_BATCH_SIZE;
import static edu.nyu.Constant.TOP_WORDS_COUNT;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnParent;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;
import edu.nyu.Connector;

public class WordCounter extends BaseBasicBolt {

	private static final long serialVersionUID = 5968327715395972088L;
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
	@SuppressWarnings("rawtypes")
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

		if(counters.size() > WORDS_WRITE_BATCH_SIZE){
			writeToCassandra();
		}

		String str = input.getString(0);
		/**
		 * If the word dosn't exist in the map we will create
		 * this, if not We will add 1 
		 */
		if(!counters.containsKey(str)){
			counters.put(str, 1);
		}else{
			int c = counters.get(str) + 1;
			counters.put(str, c);
		}
	}

	private void writeToCassandra(){
		connector = new Connector();
		try {
			client = connector.connect();

			SortedSet<Map.Entry<String, Integer>> sortedCounter = 
					new TreeSet<Map.Entry<String, Integer>>(new Comparator<Map.Entry<String, Integer>>() {
						@Override
						public int compare(Entry<String, Integer> o1,
								Entry<String, Integer> o2) {
							return o2.getValue().compareTo(o1.getValue()); //enforce descending
						}
					});

			sortedCounter.addAll(counters.entrySet());

			long timestamp = System.currentTimeMillis();
			ColumnParent parent = new ColumnParent("wordCount");

			int count=0; 

			for(Map.Entry<String, Integer> entry : sortedCounter){
				if(count < TOP_WORDS_COUNT) count++;
				else break;

				Column idColumn;
				try {
					idColumn = new Column(toByteBuffer(entry.getKey()));
					idColumn.setValue(toByteBuffer(entry.getValue().toString()));
					idColumn.setTimestamp(timestamp);
					client.insert(toByteBuffer(timestamp+""), parent, idColumn, CL);
//					System.out.println(entry.getKey()+"->"+entry.getValue());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			sortedCounter = null;
			counters.clear();
			System.out.println("Batch Written");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public static ByteBuffer toByteBuffer(String value) throws UnsupportedEncodingException{
		//		return ByteBufferUtil.bytes(value);
		return ByteBuffer.wrap(value.getBytes(UTF8));
	}

	//	public static ByteBuffer toByteBuffer(long value) throws UnsupportedEncodingException{
	//		return ByteBufferUtil.bytes(value);
	//	}
}
