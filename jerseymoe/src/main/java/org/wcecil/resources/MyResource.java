package org.wcecil.resources;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ContainerRequest;
import org.wcecil.application.MyApp;
import org.wcecil.business.CommonBusiness;
import org.wcecil.business.UserBusiness;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("common")
public class MyResource {
	@Context
	ContainerRequest req;

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to
	 * the client as "text/plain" media type.
	 * 
	 * @return String that will be returned as a text/plain response.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/user/get")
	public Response getUser(@HeaderParam(MyApp.AUTH_TOKEN) String uuidHeader,
			@QueryParam("uuid") String uuid) {
		if (uuid == null) {
			uuid = uuidHeader;
		}
		return UserBusiness.getUser(req, uuid);

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/test")
	public Response getItTest(@HeaderParam(MyApp.AUTH_TOKEN) String uuid) {

		return UserBusiness.getUser(req, uuid);

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/list")
	public Response getMethodsAvailable(
			@HeaderParam(MyApp.AUTH_TOKEN) String uuid) {

		return CommonBusiness.getAllResources();

	}
}
