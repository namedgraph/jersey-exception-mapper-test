package jersey.test.release_conn;

import java.io.IOException;
import java.net.URI;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

/**
 *
 * @author Martynas Jusevičius <martynas@atomgraph.com>
 */
public class ReleaseConnTest extends JerseyTest {

    private final static int MAX_TOTAL = 30;
    private final static int MAX_PER_ROUTE = 20;
    
    public class App extends ResourceConfig
    {
        private final Client client;
        
        public App()
        {
            ClientConfig config = new ClientConfig();
            PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
            cm.setMaxTotal(MAX_TOTAL);
            cm.setDefaultMaxPerRoute(MAX_PER_ROUTE);
            config.connectorProvider(new ApacheConnectorProvider());
            config.property(ApacheClientProperties.CONNECTION_MANAGER , cm);

            client = ClientBuilder.newClient(config);
        }
        
        public Client getClient()
        {
            return client;
        }
        
    }
    
    @Override
    public App configure()
    {
        App app = new App();
        app.property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_SERVER, "WARNING");
        app.register(LoopbackRequestResource.class);
        app.register(app);
        
        return app;
    }

    @Path("test")
    public static class LoopbackRequestResource {
        
        private final App app;
        
        @Inject
        LoopbackRequestResource(App app) {
            this.app = app;
        }
        
        @GET
        public String get() {
            try (Response cr = app.getClient().target(URI.create("https://www.google.com")).
                    register(new CacheControlFilter(CacheControl.valueOf("no-cache"))). // uncomment ant the test will not fail (will run indefinitely)
                    request().get())
            {
                return cr.readEntity(String.class);
            }
        }
    }

    @Test(expected = InternalServerErrorException.class)
    public void testLoopback() {
        do
        {
            target("test").request().get(String.class);
        }
        while (true);
    }
    
    public static class CacheControlFilter implements ClientRequestFilter
    {

        private final CacheControl cacheControl;

        public CacheControlFilter(CacheControl cacheControl)
        {
            this.cacheControl = cacheControl;
        }

        @Override
        public void filter(ClientRequestContext request) throws IOException
        {
            request.getHeaders().putSingle(HttpHeaders.CACHE_CONTROL, getCacheControl());
        }

        public CacheControl getCacheControl()
        {
            return cacheControl;
        }

    }
    
}
