package org.wcecil.business;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.wcecil.data.DataStorageAccess;

public class DataStorageBusiness {

	public static Response getKey(String uuid, String key) {
		if (uuid == null || key == null) {
			return Response
					.status(Status.BAD_REQUEST)
					.entity("A key is required as well as a authorization token.")
					.build();
		}

		DataStorageAccess.getKeyForOwner(uuid, key);
		
		//well we failed
		return Response.status(Status.NOT_FOUND)
				.entity("Nothing found for " + key + "@" + key).build();
	}

}
