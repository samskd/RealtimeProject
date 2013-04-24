package edu.nyu.util;

import junit.framework.Assert;

import org.junit.Test;

public class StopwordsTest {

	@Test
	public void test() {
		
		Assert.assertEquals(536, StopWords.getStopWordSet().size());
		
		Assert.assertTrue(StopWords.isStopWord("the"));
		Assert.assertFalse(StopWords.isStopWord("Samit"));
	}

}
