package org.wcecil.util;

import java.net.UnknownHostException;
import java.util.Arrays;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;

public class MongoUtil {

	private static DB instanceDB;
	private static MongoClient instance;
	private static String dbName;

	public static DB getInstanceDB() throws UnknownHostException,
			RuntimeException {
		if (instanceDB == null) {
			instanceDB = getMongoDB();
		}
		return instanceDB;
	}

	public static MongoClient getInstance() throws UnknownHostException,
			RuntimeException {
		if (instance == null) {

			instance = getMongo();

		}
		return instance;
	}

	/**
	 * opens a mondodb connection
	 * 
	 * @return
	 * @throws UnknownHostException
	 * @throws Exception
	 */
	private static MongoClient getMongo() throws RuntimeException,
			UnknownHostException {
		// check if local or on openshift
		String host = System.getenv("OPENSHIFT_MONGODB_DB_HOST");
		if (host == null) {
			MongoClient mongoClient = new MongoClient("localhost");
			dbName = "tomcat";
			return mongoClient;
		}
		int port = Integer.parseInt(System.getenv("OPENSHIFT_MONGODB_DB_PORT"));
		dbName = System.getenv("OPENSHIFT_APP_NAME");
		String username = System.getenv("OPENSHIFT_MONGODB_DB_USERNAME");
		String password = System.getenv("OPENSHIFT_MONGODB_DB_PASSWORD");

		MongoCredential credits = MongoCredential.createMongoCRCredential(
				username, dbName, password.toCharArray());

		MongoClient mongoClient = new MongoClient(
				new ServerAddress(host, port), Arrays.asList(credits));
		mongoClient.setWriteConcern(WriteConcern.SAFE);

		return mongoClient;
	}

	public static DB getMongoDB() throws UnknownHostException, RuntimeException {

		Mongo mongoClient = getInstance();
		DB db = mongoClient.getDB(dbName);
		if (db == null) {
			throw new RuntimeException("Not able to authenticate with MongoDB");
		}
		return db;

	}
	
	public static MongoTemplate getTemplate() throws UnknownHostException, RuntimeException{
		MongoTemplate temp;
		
		temp = new MongoTemplate(getInstance(), dbName);
		
		return temp;
	}
	
	public static void clear(){
		if(instanceDB!=null){
			instanceDB = null;
		}
		if(instance!=null){
			instance.close();
			instance = null;
		}
	}
}
