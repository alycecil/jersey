package org.wcecil.business;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.server.ContainerRequest;
import org.wcecil.beans.UserBean;
import org.wcecil.data.UsersDataAccess;

public class UserBusiness {
	public static UserBean getUserByUUID(String uuid) {
		return UsersDataAccess.getUser(uuid);
	}

	public static Response getUser(ContainerRequest req, String uuid) {
		if(uuid==null){
			return Response.status(Status.NOT_FOUND).entity("UUID Not Specified").build();
		}
		UserBean user = getUserByUUID(uuid);
		
		if(user==null){
			return Response.status(Status.NOT_FOUND).entity("UUID Not valid:"+uuid).build();
		}else{
			return Response.status(Status.OK).entity(user.getObjectAsJsonString()).build();
		}
	}

}
