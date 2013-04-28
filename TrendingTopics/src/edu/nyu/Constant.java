package edu.nyu;

import org.apache.cassandra.thrift.ConsistencyLevel;

public class Constant {
	
		public static final String UTF8 = "UTF8";
	    public static final String KEYSPACE = "MyKeyspace";
	    public static final ConsistencyLevel CL = ConsistencyLevel.ONE;
		public static final String HOST = "loalhost";
		public static final int PORT = 9160;
		public static final String CASSANDRA_BATCH_MAX_SIZE = "cassandra.batch.max_size";
}