package org.wcecil.business;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.wcecil.beans.DataBean;
import org.wcecil.data.DataStorageAccess;

public class DataStorageBusiness {

	public static Response getKey(String uuid, String key) {
		if (uuid == null || key == null) {
			return Response
					.status(Status.BAD_REQUEST)
					.entity("A key is required as well as a authorization token.")
					.build();
		}

		DataBean data = DataStorageAccess.getKeyForOwner(uuid, key);

		if (data != null) {
			data.setId(null);

			return Response.status(Status.OK).entity(data).build();
		}

		// well we failed
		return Response.status(Status.NOT_FOUND)
				.entity("Nothing found for " + key + "@" + uuid).build();
	}

	public static Response setKeyValue(String uuid, String key, Object value) {
		if (uuid == null || key == null || key.isEmpty()) {
			return Response
					.status(Status.BAD_REQUEST)
					.entity("A key is required as well as a authorization token.")
					.build();
		}

		// setup
		DataBean data = new DataBean();
		data.setKey(key);
		data.setOwner(uuid);
		data.setValue(value);

		// save
		DataBean result = DataStorageAccess.setKeyForOwner(data);

		if (result == null) {
			data.setId(null);

			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Unable to save").build();
		}else{
			result.setId(null);

			return Response.status(Status.OK).entity(result).build();
		}
	}

}
