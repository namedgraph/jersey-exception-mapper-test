package jersey.test.constructor_exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ConstructorExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable e) {
        Response response = Response.status(500)
                    .entity("Mapped")
                    .build();

        return response;
    }
}