package org.wcecil.filters;

import java.io.IOException;
import java.util.UUID;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.wcecil.application.MyApp;
import org.wcecil.beans.UserBean;
import org.wcecil.data.UsersDataAccess;

public class IdentificationFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext req) throws IOException {
		// get headers
		MultivaluedMap<String, String> headers = req.getHeaders();

		String token = null;
		if (headers != null) {
			System.out.println(headers.toString());

			token = verifyLogin(req, headers);

			req.getHeaders().add(MyApp.AUTH_TOKEN, token);

			updateCookie(req, token, headers);

		}

	}

	public String verifyLogin(ContainerRequestContext req,
			MultivaluedMap<String, String> headers) {
		String token = null;
		boolean hasToken = false;
		// get header value for Authorization : Token XXXX
		if (headers.containsKey(MyApp.AUTH)) {
			for (String value : headers.get(MyApp.AUTH)) {

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
			token = getCookie(headers);
			if (token == null) {
				token = UUID.randomUUID().toString().toUpperCase();
			}
			headers.add(MyApp.AUTH, "Token " + token);

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
		String session = getCookie(headers);

		if (session != null) {
			UserBean user = UsersDataAccess.getUser(token);
			user.setCookie(session);
			UsersDataAccess.saveUser(user);
		}
	}

	public String getCookie(MultivaluedMap<String, String> headers) {
		String session = null;
		if (headers.containsKey(MyApp.COOKIE)) {

			for (String value : headers.get(MyApp.COOKIE)) {

				if (value != null) {
					value = value.toUpperCase().trim();
					if (value.startsWith(MyApp.JSESSIONID + "=")) {

						session = value.substring((MyApp.JSESSIONID + "=")
								.length());

						if (session.indexOf(';') > 0) {
							session = session
									.substring(0, session.indexOf(';'));
						}

						session = session.trim();
					}
				}
			}
		}
		return session;

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
