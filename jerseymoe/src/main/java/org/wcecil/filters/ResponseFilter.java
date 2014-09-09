package org.wcecil.filters;

import java.io.IOException;
import java.util.Collection;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import org.wcecil.application.MyApp;
import org.wcecil.beans.BeanBase;

public class ResponseFilter implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext req,
			ContainerResponseContext resp) throws IOException {
		String token = req.getHeaderString(MyApp.AUTH_TOKEN);
		if (token != null && !token.isEmpty()) {
			resp.getHeaders().add(MyApp.AUTH_TOKEN, token);

			// TODO figure out a better way to do this
			resp.getHeaders().add("Set-Cookie",
					"JSESSIONID=" + token + "; Path=/jerseymoe/");
		}
		
		
		//allow requests from anywhere
		
		resp.getHeaders().add("Access-Control-Allow-Origin:", "*");
		
		resp.getHeaders().add("X-Powered-By:", "JerseyMoe");
		
		if (resp.getEntity() == null) {
			return;
		}
		if (resp.getEntity() instanceof BeanBase) {
			BeanBase bean = (BeanBase) resp.getEntity();
			resp.setEntity(bean.getObjectAsJsonString(true));
		}

		if (resp.getEntity() instanceof Collection) {
			String json = null;
			try {
				json = BeanBase.getObjectAsJsonString(resp.getEntity(), true);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (json != null) {
				resp.setEntity(json);
			}
		}

	}
}
