package edu.nyu.util;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class TokensTest {

	@Test
	public void test() {
		try{
			String text = "Hello how are you Samit";
			
			List<String> tokens = Tokenizer.tokenize(text);
			
			System.out.println(tokens);
			
			}catch(Exception e){
				e.printStackTrace();
			}
	}

}
