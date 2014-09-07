package org.wcecil.application;

import org.glassfish.jersey.server.ResourceConfig;

public class MyApp extends ResourceConfig {
	public static String APP_KEY_PREFIX = "MyApp:";
	public static String APP_KEY_POSTFIX = ":v0.1";
	
	public MyApp() {
		packages("org.wcecil.resources");
	}

	public static String getKey(String pKEY) {
		return APP_KEY_PREFIX+pKEY+APP_KEY_POSTFIX;
	}
}
