package jersey.test.mapper_injection;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import org.glassfish.hk2.api.Factory;

/**
 *
 * @author Martynas Jusevičius <martynas@atomgraph.com>
 */
public class PerRequestInterfaceFactory implements Factory<PerRequestInterface> {

    @Context UriInfo uriInfo;

    @Override
    public PerRequestInterface provide() {
        FieldInterface field = getField();
        
        if (getField() != null) return new PerRequestClass(uriInfo.getRequestUri(), field);
        
        return null;
    }

    @Override
    public void dispose(PerRequestInterface t) {
    }
    
    FieldInterface getField() {
        return null;
    }
    
}
