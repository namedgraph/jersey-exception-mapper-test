package jersey.test.mapper_injection;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import jersey.test.App;
import org.glassfish.jersey.test.JerseyTest;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Martynas Jusevičius <martynas@atomgraph.com>
 */
public class MapperInjectionTest extends JerseyTest {
    
    @Override
    protected Application configure() {
        return new App();
    }
 
    @Test
    public void test() {
        Response response = target("mapper-exception").request().get();
        
        assertEquals(NotFoundExceptionMapper.STATUS, response.getStatus());
        assertEquals(getBaseUri().resolve("mapper-exception"), response.getLocation());
    }
    
    
}
