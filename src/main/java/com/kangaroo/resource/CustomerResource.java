package com.kangaroo.resource;

import com.kangaroo.model.Customer;
import com.kangaroo.utility.Constants;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

@Path("/customer")
public class CustomerResource {

    // Default customer
    private Customer demoCustomer = new Customer("01","Hikmat Dhamee");

    // The @Context annotation allows us to have certain contextual objects
    // injected into this class.
    // UriInfo object allows us to get URI information (no kidding).
    @Context
    UriInfo uriInfo;

    // Another "injected" object. This allows us to use the information that's
    // part of any incoming request.
    // We could, for example, get header information, or the requestor's address.
    @Context
    Request request;

    // Basic "is the service running" test
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String responseAsReady() {
        return "Demo service is ready!";
    }

    // Basic "is the service running" test
    @GET
    @Path("/demo")
    @Produces("application/xml")
    public Customer testService() {
        return demoCustomer;
    }

    // Use data from the client source to create a new Person object, returned in JSON format.
    @POST
    @Path("/add")
    @Consumes("application/json")
    @Produces("text/plain")
    public String addPerson( final Customer customer ) {

        // add new customer
        if (indexCustomer(customer)){
            return Constants.SUCCESS_MSG;
        }else {
            return Constants.FAIL_MSG;
        }
    }

    // index customer
    private boolean indexCustomer(final  Customer customer) {
        return true;
    }
}
