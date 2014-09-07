package org.wcecil.application;

import org.glassfish.jersey.server.ResourceConfig;

public class MyApp extends ResourceConfig {
	public MyApp() {
		packages("org.wcecil.resources");
	}
}
