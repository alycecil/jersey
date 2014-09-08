package org.wcecil.resources;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.wcecil.application.MyApp;
import org.wcecil.business.DataStorageBusiness;

public class DataResource {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/get")
	public Response getIt(@HeaderParam(MyApp.AUTH_TOKEN) String uuidHeader, @QueryParam("key") String key){
		return DataStorageBusiness.getKey(uuidHeader,key);
	}
}
