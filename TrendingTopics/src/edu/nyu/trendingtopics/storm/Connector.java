package edu.nyu.trendingtopics.storm;

import static edu.nyu.trendingtopics.storm.Constant.HOST;
import static edu.nyu.trendingtopics.storm.Constant.KEYSPACE;
import static edu.nyu.trendingtopics.storm.Constant.PORT;

import org.apache.cassandra.thrift.Cassandra.Client;
import org.apache.cassandra.thrift.InvalidRequestException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;


/**
 * Creates a {@link Client} object to connect to Cassandra. This object is 
 * used to modify the state of Cassandra datastore.
 * 
 * @author soobokshin
 * */
public class Connector {
	
	private TTransport tr = new TSocket(HOST, PORT);
	
	/**
	 * Returns the Cassandra Client object used to modify the state of Cassandra keyspace. 
	 * 
	 * @return Cassandra client object.
	 * */
	public Client connect() throws InvalidRequestException, TException {
		TFramedTransport tf = new TFramedTransport(tr);
		TProtocol proto = new TBinaryProtocol(tf);
		Client client= new Client(proto);
		tr.open();
		client.set_keyspace(KEYSPACE);
		return client;
	}
	
	/**
	 * Closes the Input/Output stream for this client.
	 * */
	public void close(){
		tr.close();
	}
	
	/**
	 * Overrides the Object method to force closing of the Input/Output stream for this client.
	 * */
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		tr.close();
	}

}