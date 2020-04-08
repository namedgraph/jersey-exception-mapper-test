package jersey.test.constructor_exception;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/constructor-exception")
public class ConstructorExceptionResource {

    public ConstructorExceptionResource() {
        throw new RuntimeException();
    }
    
    @GET
    public Response get()
    {
        //throw new RuntimeException();
        return Response.ok().build();
    }
    
}