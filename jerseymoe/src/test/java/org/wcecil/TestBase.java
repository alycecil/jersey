package org.wcecil;

import java.util.Random;

import junit.framework.Assert;

import org.junit.Test;
import org.wcecil.application.MyApp;

@SuppressWarnings("deprecation")
public class TestBase extends Assert {

	public static Random r = new Random();
	
	@Test
	public void test() {
		assertTrue("core test", true);
		assertNotNull("core test",MyApp.APP_KEY_PREFIX);
	}

	

}
