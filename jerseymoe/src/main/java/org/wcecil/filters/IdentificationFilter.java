package org.wcecil.filters;

import java.io.IOException;
import java.util.UUID;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;

public class IdentificationFilter implements ContainerRequestFilter {

	private static final String AUTH = "Authorization";

	@Override
	public void filter(ContainerRequestContext req) throws IOException {
		// get headers
		MultivaluedMap<String, String> headers = req.getHeaders();

		if (headers != null) {
			System.out.println(headers.toString());

			boolean hasToken = false;
			String token = null;

			// get header value for Authorization : Token XXXX
			if (headers.containsKey(AUTH)) {
				for (String value : headers.get(AUTH)) {

					if (value != null) {
						value = value.toUpperCase().trim();
						if(value.startsWith("TOKEN ")){
							hasToken=true;
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
			}else{
				verifyToken(req, token);
			}

		}
	}

	private void verifyToken(ContainerRequestContext req, String token) {
		// TODO Auto-generated method stub
		
		
	}

	private void createToken(ContainerRequestContext req, String token) {
		// TODO Auto-generated method stub
		
	}

}
