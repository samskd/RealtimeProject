package edu.nyu.trendingtopics.storm.util;

import junit.framework.Assert;

import org.junit.Test;

import edu.nyu.trendingtopics.storm.util.StopWords;

public class StopwordsTest {

	@Test
	public void test() {
		
		Assert.assertTrue(StopWords.isStopWord("the"));
		Assert.assertFalse(StopWords.isStopWord("Samit"));
		Assert.assertTrue(StopWords.isStopWord("fuck"));
	}

}
