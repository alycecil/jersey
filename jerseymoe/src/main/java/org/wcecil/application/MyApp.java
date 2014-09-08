package org.wcecil.application;

import org.glassfish.jersey.server.ResourceConfig;
import org.wcecil.filters.IdentificationFilter;
import org.wcecil.filters.ResponseFilter;

public class MyApp extends ResourceConfig {
	public static String APP_KEY_PREFIX = "MyApp:";
	public static String APP_KEY_POSTFIX = ":v0.1";
	public static final String AUTH = "Authorization";
	public static final String AUTH_TOKEN = "Auth_Token";
	public static final String COOKIE = "Cookie";
	public static final String JSESSIONID = "JSESSIONID";
	
	public MyApp() {
		packages("org.wcecil.resources");
		
		//registered filters
		register(IdentificationFilter.class);
		register(ResponseFilter.class);
	}

	public static String getKey(String pKEY) {
		return APP_KEY_PREFIX+pKEY+APP_KEY_POSTFIX;
	}
}
