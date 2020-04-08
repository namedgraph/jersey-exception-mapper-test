package jersey.test.mapper_injection;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/mapper-exception")
public class MapperInjectionResource {

    @GET
    public Response get()
    {
        throw new NotFoundException();
    }
    
}