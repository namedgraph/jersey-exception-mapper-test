package jersey.test.mapper_injection;

import java.net.URI;

/**
 *
 * @author Martynas Jusevičius <martynas@atomgraph.com>
 */
public class PerRequestClass {
    
    private URI uri;
    
    public PerRequestClass(URI uri) {
        this.uri = uri;
    }
    
    public URI getURI() {
        return uri;
    }
    
}
