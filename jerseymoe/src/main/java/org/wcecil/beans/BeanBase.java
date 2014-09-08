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
		return getObjectAsJsonString(this);
	}

	/**
	 * Gets as a not null
	 * @param o
	 * @return
	 */
	@JsonIgnore
	public String getObjectAsJsonString(Object o) {
		String json = null;

		ObjectMapper mapper = new ObjectMapper();

		ObjectWriter writer = mapper.writer();
		mapper.setSerializationInclusion(Include.NON_NULL);
		
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
