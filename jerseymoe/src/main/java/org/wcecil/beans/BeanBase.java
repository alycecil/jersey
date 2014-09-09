package org.wcecil.beans;

import java.io.IOException;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

@Document
public abstract class BeanBase {

	/**
	 * Turn object into json
	 * 
	 * @return
	 */
	@JsonIgnore
	public String getObjectAsJsonString() {
		return getObjectAsJsonString(this, false);
	}
	
	@JsonIgnore
	public String getObjectAsJsonString(boolean prettyPrint) {
		return getObjectAsJsonString(this, prettyPrint);
	}

	
	/**
	 * 
	 * @param o
	 * @return
	 */
	public static String getObjectAsJsonString(Object o){
		return getObjectAsJsonString(o, false);
	}
	
	/**
	 * Gets a not null object into a jackson's json string
	 * 
	 * @param o
	 * @param prettyPrint 
	 * @return
	 */
	@JsonIgnore
	public static String getObjectAsJsonString(Object o, boolean prettyPrint) {
		String json = null;

		ObjectMapper mapper = new ObjectMapper();

		mapper.setSerializationInclusion(Include.NON_NULL);
		
		ObjectWriter writer = null;

		if (prettyPrint) {
			writer = mapper.writerWithDefaultPrettyPrinter();
		} else {
			writer = mapper.writer();
		}
		

		try {
			json = writer.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return json;
	}

	/**
	 * Read object of specified class from string
	 * 
	 * @param json
	 * @param c
	 * @return
	 */
	@JsonIgnore
	public static Object getBeanFromJson(String json, Class<?> c) {
		ObjectMapper mapper = new ObjectMapper();

		ObjectReader reader = mapper.reader(c);
		Object result = null;
		try {
			result = reader.readValue(json);
		} catch (JsonProcessingException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		return result;
	}

	@Override
	@JsonIgnore
	public boolean equals(Object obj) {
		if (obj != null) {
			if (obj instanceof BeanBase) {

				BeanBase b = (BeanBase) obj;

				return getObjectAsJsonString()
						.equals(b.getObjectAsJsonString());
			} else if (obj instanceof String) {
				return getObjectAsJsonString().equals(obj);
			}
		}

		return false;
	}
}
