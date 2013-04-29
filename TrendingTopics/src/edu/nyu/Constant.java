package edu.nyu;

import org.apache.cassandra.thrift.ConsistencyLevel;

public class Constant {
	
		public static final String UTF8 = "UTF8";
	    public static final String KEYSPACE = "TrendingTopics";
	    public static final ConsistencyLevel CL = ConsistencyLevel.ONE;
		public static final String HOST = "localhost";
		public static final int PORT = 9160;
		public static final String CASSANDRA_BATCH_MAX_SIZE = "cassandra.batch.max_size";
		public static final int WORDS_WRITE_BATCH_SIZE  = 1000;
		public static final int TOP_WORDS_COUNT  = 100;
}