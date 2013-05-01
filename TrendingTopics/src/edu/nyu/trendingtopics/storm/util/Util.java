package edu.nyu.trendingtopics.storm.util;

/**
 * Class defined utility methods for this application.
 * 
 * @author samitpatel
 * */
public class Util {

	private static long _batchNumber = 0;
	
	/**
	 * Returns the next batch number. This method is thread-safe
	 * 
	 * @return Next batch number
	 * */
	public static synchronized long getNextBatchNumber(){
		_batchNumber++;
		return _batchNumber;
	}
}
