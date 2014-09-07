package org.wcecil.filters;

import java.io.IOException;
import java.util.UUID;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.wcecil.beans.UserBean;
import org.wcecil.data.UsersDataAccess;

public class IdentificationFilter implements ContainerRequestFilter {

	private static final String AUTH = "Authorization";
	private static final String AUTH_TOKEN = "Auth_Token";
	private static final String COOKIE = "Cookie";

	@Override
	public void filter(ContainerRequestContext req) throws IOException {
		// get headers
		MultivaluedMap<String, String> headers = req.getHeaders();

		if (headers != null) {
			System.out.println(headers.toString());

			boolean hasToken = false;
			String token = null;

			token = verifyLogin(req, headers, hasToken);

			req.getHeaders().add(AUTH_TOKEN, token);

			updateCookie(req, token, headers);

		}
	}

	public String verifyLogin(ContainerRequestContext req,
			MultivaluedMap<String, String> headers, boolean hasToken) {
		String token = null;
		// get header value for Authorization : Token XXXX
		if (headers.containsKey(AUTH)) {
			for (String value : headers.get(AUTH)) {

				if (value != null) {
					value = value.toUpperCase().trim();
					if (value.startsWith("TOKEN ")) {
						hasToken = true;
						token = value.substring(6);
					}
				}
			}
		}

		if (!hasToken) {
			// otherwise gen a guid and set it in
			token = UUID.randomUUID().toString().toUpperCase();
			headers.add(AUTH, "Token " + token);

			createToken(req, token);
		} else {
			verifyToken(req, token);
		}
		return token;
	}

	private void updateCookie(ContainerRequestContext req, String token,
			MultivaluedMap<String, String> headers) {
		if (token == null) {
			return;
		}
		if (headers.containsKey(COOKIE)) {
			String session = null;
			for (String value : headers.get(COOKIE)) {

				if (value != null) {
					value = value.toUpperCase().trim();
					if (value.startsWith("JSESSIONID=")) {

						session = value.substring("JSESSIONID=".length());
					}
				}
			}

			if (session != null) {
				UserBean user = UsersDataAccess.getUser(token);
				user.setCookie(session);
				UsersDataAccess.saveUser(user);
			}
		}
	}

	private void verifyToken(ContainerRequestContext req, String token) {
		UserBean user = UsersDataAccess.getUser(token);

		if (user == null) {
			req.abortWith(Response.status(Status.UNAUTHORIZED)
					.entity("Not Authorized").build());
		}
	}

	private void createToken(ContainerRequestContext req, String token) {
		UserBean user = new UserBean();
		user.setUuid(token);

		UsersDataAccess.createUser(user);
	}

}
