package edu.nyu.trendingtopics.storm.util;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import edu.nyu.trendingtopics.storm.util.Tokenizer;

public class TokensTest {

	@Test
	public void test() {
		try{
			String text = "Hello how are you Samit";

			List<String> tokens = new Tokenizer().tokenize(text);
			System.out.println(tokens);
			Assert.assertEquals(1, tokens.size()); //[samit]

		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
