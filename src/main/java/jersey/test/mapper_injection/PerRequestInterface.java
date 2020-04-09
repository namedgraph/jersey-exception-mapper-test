package jersey.test.mapper_injection;

import java.net.URI;

/**
 *
 * @author Martynas Jusevičius <martynas@atomgraph.com>
 */
public interface PerRequestInterface {
    
    URI getURI();
    
    FieldInterface getField();
    
}
