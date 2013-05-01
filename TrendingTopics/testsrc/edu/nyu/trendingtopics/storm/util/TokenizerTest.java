package edu.nyu.trendingtopics.storm.util;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import edu.nyu.trendingtopics.storm.util.Tokenizer;

public class TokenizerTest {

	@Test
	public void test() {
		String tweet = "Hi #ipl";
		
		List<String> list = new Tokenizer().tokenize(tweet);
		System.out.println(list);
		Assert.assertEquals(2, list.size());
	}

}
