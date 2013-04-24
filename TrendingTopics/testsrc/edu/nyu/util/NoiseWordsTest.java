package edu.nyu.util;

import org.junit.Assert;
import org.junit.Test;

public class NoiseWordsTest {

	@Test
	public void test() {

		Assert.assertFalse(NoiseWords.isNoiseWord("samit"));
		Assert.assertTrue(NoiseWords.isNoiseWord("?"));
		Assert.assertTrue(NoiseWords.isNoiseWord("??"));
		Assert.assertTrue(NoiseWords.isNoiseWord("???"));
		Assert.assertTrue(NoiseWords.isNoiseWord("123345"));
		Assert.assertFalse(NoiseWords.isNoiseWord("ksdj134bf"));
	}

}
