package edu.nyu;

import static edu.nyu.Constant.HOST;
import static edu.nyu.Constant.KEYSPACE;
import static edu.nyu.Constant.PORT;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.InvalidRequestException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class Connector {
	
	TTransport tr= new TSocket(HOST,PORT);
	public Cassandra.Client connect() throws InvalidRequestException, TException {
		TFramedTransport tf = new TFramedTransport(tr);
		TProtocol proto = new TBinaryProtocol(tf);
		Cassandra.Client client= new Cassandra.Client(proto);
		tr.open();
		client.set_keyspace(KEYSPACE);
		return client;
	}
	public void close(){
		tr.close();
	}
 
}