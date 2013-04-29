package edu.nyu.storm.bolts;

import static edu.nyu.Constant.CL;
import static edu.nyu.Constant.WORDS_WRITE_BATCH_SIZE;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.utils.ByteBufferUtil;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;
import edu.nyu.Connector;

public class WordCounter extends BaseBasicBolt {
<<<<<<< HEAD
	private static final long serialVersionUID = 5968327715395972088L;
=======
	private static final Logger LOG = Logger.getLogger(WordCounter.class);
>>>>>>> change wordcount
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
						client.insert(toByteBuffer(timestamp), parent, idColumn, CL);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				counters.clear();
		//		System.out.println("clear");
			} catch (Exception e1) {
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
			int c = counters.get(str) + 1;
			counters.put(str, c);
		}
	}

	public static ByteBuffer toByteBuffer(String value) throws UnsupportedEncodingException{
		return ByteBufferUtil.bytes(value);
//		return ByteBuffer.wrap(value.getBytes(UTF8));
	}

	public static ByteBuffer toByteBuffer(long value) throws UnsupportedEncodingException{
		return ByteBufferUtil.bytes(value);
	}
}
