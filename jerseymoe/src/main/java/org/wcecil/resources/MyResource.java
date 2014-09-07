package org.wcecil.resources;

import java.net.UnknownHostException;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.wcecil.util.MongoUtil;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("common")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt(@HeaderParam("user-agent") String userAgent) {
        return "Got it from '"+userAgent+"'.";
    }
    
    
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/test")
    public String testMongo(@HeaderParam("user-agent") String userAgent) {
    	String msg ="none";
    	try {
			DB db = MongoUtil.mongo();
			
			DBCollection testCol = db.getCollection("test");
			BasicDBObject dbo = new BasicDBObject();
			dbo.append("last_agent", userAgent);
			
			testCol.insert(dbo);
			
			msg ="done";
		} catch (UnknownHostException e) {
			e.printStackTrace();
			msg = e.getLocalizedMessage();
		} catch (RuntimeException e) {
			e.printStackTrace();
			msg = e.getLocalizedMessage();
		}
    	
        return "Got it from '"+userAgent+"'. ack:"+msg;
    }
}
