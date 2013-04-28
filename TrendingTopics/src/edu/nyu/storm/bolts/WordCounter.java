package edu.nyu.storm.bolts;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;
import org.apache.cassandra.thrift.ColumnPath;
import org.apache.cassandra.thrift.InvalidRequestException;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
//import org.apache.cassandra.thrift.Clock;
import edu.nyu.Connector;
import static edu.nyu.Constant.CL;
import static edu.nyu.Constant.HOST;
import static edu.nyu.Constant.KEYSPACE;
import static edu.nyu.Constant.PORT;
import static edu.nyu.Constant.UTF8;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

public class WordCounter extends BaseBasicBolt {
	private static final Logger LOG = Logger.getLogger(WordCounter.class);
	private static Cassandra.Client client;
	private static Connector connector;
	private static final long serialVersionUID = 1786534081785862087L;
	
	int id;
	String name;
	Map<String, Integer> counters;

	/**
	 * At the end of the spout (when the cluster is shutdown
	 * We will show the word counters
	 */
	@Override
	public void cleanup() {
		System.out.println("-- Word Counter ["+name+"-"+id+"] --");
		for(Map.Entry<String, Integer> entry : counters.entrySet()){
			System.out.println(entry.getKey()+": "+entry.getValue());
		}
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

		
		 connector = new Connector();
		 try {
			client = connector.connect();
		} catch (InvalidRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//	 Clock clock = new Clock(System.currentTimeMillis()); 
		 String ColumnFamily = "WordCount";//this is column family
		 String str = input.getString(0);
		 byte[] userIDKey = str.getBytes();
		 
		ColumnPath path = new ColumnPath(ColumnFamily);
		path.setColumn(ByteBuffer.wrap("count".getBytes(UTF8)));
		
		ColumnOrSuperColumn cos = client.get(ByteBuffer.wrap(userIDKey), path, CL);
		ColumnParent cp = new ColumnParent(ColumnFamily);
		if(cos==null){
			Column c1 = new Column(ByteBuffer.wrap("count".getBytes(UTF8)), ByteBuffer.wrap("1".getBytes(UTF8)), System.currentTimeMillis());
			client.insert(ByteBuffer.wrap(userIDKey), cp, c1, CL);
		}else{
			Column col =cos.column;
			String count =new String(col.getValue(),UTF8);
			count+=1;
			Column c1 = new Column(ByteBuffer.wrap("count".getBytes(UTF8)), ByteBuffer.wrap(count.getBytes(UTF8)), System.currentTimeMillis());
			client.insert(ByteBuffer.wrap(userIDKey), cp, c1, CL);
	
		}
	}
}
