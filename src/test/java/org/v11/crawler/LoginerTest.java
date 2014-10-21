package org.v11.crawler;

import org.junit.Test;
import org.v11.crawler.answer.Loginer;

public class LoginerTest {
	@Test
	public void testLogin(){
		Loginer loginer = new Loginer();
		loginer.login();
	}
}
