package org.wcecil;

import junit.framework.Assert;

import org.junit.Test;
import org.wcecil.application.MyApp;

@SuppressWarnings("deprecation")
public class TestBase extends Assert {

	@Test
	public void test() {
		assertTrue("core test", true);
		assertNotNull("core test",MyApp.APP_KEY_PREFIX);
	}

	

}
