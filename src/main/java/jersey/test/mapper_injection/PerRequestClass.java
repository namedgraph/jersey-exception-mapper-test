package jersey.test.mapper_injection;

import java.net.URI;

/**
 *
 * @author Martynas Jusevičius <martynas@atomgraph.com>
 */
public class PerRequestClass implements PerRequestInterface {
    
    private final URI uri;
    private final FieldInterface field;
    
    public PerRequestClass(URI uri, FieldInterface field) {
        this.uri = uri;
        this.field = field;
    }
    
    @Override
    public URI getURI() {
        return uri;
    }

    @Override
    public FieldInterface getField() {
        return field;
    }
    
}
