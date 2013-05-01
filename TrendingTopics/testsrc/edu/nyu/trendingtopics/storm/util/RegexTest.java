package edu.nyu.trendingtopics.storm.util;

import org.junit.Assert;
import org.junit.Test;

public class RegexTest {

	@Test
	public void test() {
		Assert.assertTrue("#tag".matches("^[a-zA-Z#]+$"));
	}

}
