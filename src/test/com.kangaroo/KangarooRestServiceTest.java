package com.kangaroo;

import com.kangaroo.model.Auth;
import junit.framework.TestCase;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.ClientResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

/**
 * @author Hikamt Dhamee
 * @email me.hemant.available@gmail.com
 */
public class KangarooRestServiceTest extends TestCase {
    private HttpServer server;
    Client client;

    @Before
    public void setUp() throws Exception {
        server = com.kangaroo.HttpTestServer.startServer();
        client = ClientBuilder.newClient();
    }

    @After
    public void tearDown() throws Exception {
       server.shutdownNow();
    }

    /*ignoring test */
    @Ignore
    @Test
    public void testGetIt() {
            WebTarget target = client.target(com.kangaroo.HttpTestServer.BASE_URI+"api/auth");

            ClientResponse response = target.request()
                    .get(ClientResponse.class);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }
           System.out.println("Output from Server .... \n" + response.toString());
    }
}
