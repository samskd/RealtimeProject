package edu.nyu.util;

import org.junit.Assert;
import org.junit.Test;

public class NoiseWordsTest {

	@Test
	public void test() {

		String word = "???";
		Assert.assertFalse(NoiseWords.isNoiseWord("samit"));
		Assert.assertTrue(NoiseWords.isNoiseWord(word));

	}

}
