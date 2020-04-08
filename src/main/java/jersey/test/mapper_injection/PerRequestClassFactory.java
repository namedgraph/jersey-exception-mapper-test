package jersey.test.mapper_injection;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import org.glassfish.hk2.api.Factory;

/**
 *
 * @author Martynas Jusevičius <martynas@atomgraph.com>
 */
public class PerRequestClassFactory implements Factory<PerRequestClass> {

    @Context UriInfo uriInfo;

    @Override
    public PerRequestClass provide() {
        return new PerRequestClass(uriInfo.getRequestUri());
    }

    @Override
    public void dispose(PerRequestClass t) {
    }
    
}
