package jersey.test.release_conn;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Martynas Jusevičius <martynas@atomgraph.com>
 */
public class ReleaseConnTest extends JerseyTest {

    private final static int MAX_TOTAL = 30;
    private final static int MAX_PER_ROUTE = 20;
    
    @Override
    public Application configure()
    {
        ClientConfig config = new ClientConfig();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(MAX_TOTAL);
        cm.setDefaultMaxPerRoute(MAX_PER_ROUTE);
        config.connectorProvider(new ApacheConnectorProvider());
        config.property(ApacheClientProperties.CONNECTION_MANAGER , cm);
        //config.property(ClientProperties.READ_TIMEOUT, 1_000);
        
        final Client client = ClientBuilder.newClient(config);
                
        ResourceConfig rc = new ResourceConfig();
        rc.property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_SERVER, "WARNING");
        rc.register(LoopbackRequestResource.class);
        rc.register(new AbstractBinder()
        {
            @Override
            protected void configure()
            {
                bind(client).to(Client.class);
            }
        });
        
        return rc;
    }

    @Path("loopback")
    public static class LoopbackRequestResource {
        
        private final UriInfo uriInfo;
        private final Client client;
        
        @Inject
        LoopbackRequestResource(UriInfo uriInfo, Client client) {
            this.uriInfo = uriInfo;
            this.client = client;
        }
        
        @GET
        public String get(@QueryParam("count") Integer count) {
            System.out.println("Count: " + count);
            
            if (count == 10) return "Stop";
            
            // recursive loopback request
            try (Response cr = client.target(uriInfo.getBaseUriBuilder().path(LoopbackRequestResource.class).build()).
                    queryParam("count", count + 1).request().get())
            {
                return cr.readEntity(String.class);
            }
        }
    }

    @Test
    public void testLoopback() {
        assertEquals("Stop", target("loopback").queryParam("count", 0).request().get(String.class));
    }
    
}
