package org.wcecil.data;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.wcecil.TestBase;
import org.wcecil.application.MyApp;

public class UserDataTest extends TestBase {
	@BeforeClass
	public static void beforeClass() throws Exception {
		MyApp.APP_KEY_POSTFIX = ":TEST";

		//deleteAll();
	}

	@AfterClass
	public static void afterClass() throws Exception {
		MyApp.APP_KEY_POSTFIX = ":TEST";

		//deleteAll();
	}
}
