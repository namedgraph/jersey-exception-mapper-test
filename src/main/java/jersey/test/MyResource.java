package jersey.test;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/MyResource")
public class MyResource {

    public MyResource() {
        throw new RuntimeException();
    }
    
    @GET
    public Response get()
    {
        //throw new RuntimeException();
        return Response.ok().build();
    }
    
}