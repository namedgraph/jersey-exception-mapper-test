package jersey.test.constructor_exception;

import javax.ws.rs.core.Application;
import jersey.test.App;
import org.glassfish.jersey.test.JerseyTest;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Martynas Jusevičius <martynas@atomgraph.com>
 */
public class ConstructorExceptionTest extends JerseyTest {
    
    @Override
    protected Application configure() {
        return new App();
    }
 
    @Test
    public void test() {
        final String errorMsg = target("constructor-exception").request().get(String.class);
        assertEquals("Mapped", errorMsg);
    }
    
}
