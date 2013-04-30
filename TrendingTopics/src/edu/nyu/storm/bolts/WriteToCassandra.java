package edu.nyu.storm.bolts;

import static edu.nyu.Constant.CL;
import static edu.nyu.Constant.TOP_WORDS_COUNT;
import static edu.nyu.Constant.UTF8;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.cassandra.thrift.Cassandra.Client;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnParent;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;
import edu.nyu.Connector;

public class WriteToCassandra extends BaseBasicBolt {
	

		private static final long serialVersionUID = 5168687340899971753L;

		@Override
		public void execute(Tuple tuple, BasicOutputCollector collector) {
			
			@SuppressWarnings("unchecked")
			Map<String, Integer> counters = (Map<String, Integer>) tuple.getValueByField("wordCountsMap");
			
			writeToCassandra(counters);
		}

		@Override
		public void declareOutputFields(OutputFieldsDeclarer declarer) {}
		
		
		private void writeToCassandra(Map<String, Integer> counters){
			Connector connector = new Connector();
			try {
				
				Client client = connector.connect();

				ValueComparator comparator = new ValueComparator(counters);
				SortedMap<String, Integer> sortedCounter = new TreeMap<String, Integer>(comparator);
				sortedCounter.putAll(counters);
				
				long timestamp = System.currentTimeMillis();
				ColumnParent parent = new ColumnParent("wordCount");

				int count=0; 
				for(Map.Entry<String, Integer> entry : sortedCounter.entrySet()){
					if(count < TOP_WORDS_COUNT) count++;
					else break;

					Column idColumn;
					try {
						
						//stop iterating over single words.
						if(entry.getValue() == 1) break;
						
						idColumn = new Column(toByteBuffer(entry.getKey()));
						idColumn.setValue(toByteBuffer(entry.getValue().toString()));
						idColumn.setTimestamp(timestamp);
						client.insert(toByteBuffer(timestamp+""), parent, idColumn, CL);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				sortedCounter = null;
				counters.clear();
				System.out.println("Batch Written");
			} catch (Exception e1) {
				e1.printStackTrace();
			}finally{
				connector.close();
			}
		}

		private ByteBuffer toByteBuffer(String value) throws UnsupportedEncodingException{
			return ByteBuffer.wrap(value.getBytes(UTF8));
		}

		class ValueComparator implements Comparator<String> {

		    Map<String, Integer> base;
		    public ValueComparator(Map<String, Integer> base) {
		        this.base = base;
		    }

		    // Note: this comparator imposes orderings that are inconsistent with equals.    
		    public int compare(String a, String b) {
		       if(base.get(a) >= base.get(b)) 
		    	   return -1; //descending order
		       else
		    	   return 1;
		    }
		}

}
