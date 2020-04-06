package jersey.test;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("test")
public class App extends ResourceConfig {
    
    public App() {
        register(MyResource.class);
        register(ExceptionResponseMapper.class);
    }
    
}
