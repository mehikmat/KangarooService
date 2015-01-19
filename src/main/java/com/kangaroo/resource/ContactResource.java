package com.kangaroo.resource;

import com.kangaroo.model.Contact;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import static com.kangaroo.utility.Constants.FAIL_MSG;
import static com.kangaroo.utility.Constants.SUCCESS_MSG;

@Path("/contact")
public class ContactResource {
    String result;

    private Contact demoContact = new Contact("Hemant Dhamee", "4739383873");

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
    @Path("/demo")
    @Produces("application/json")
    public Contact respondAsReady() {
        return demoContact;
    }

    // Use data from the client source to create a new Person object, returned in JSON format.
    @POST
    @Path("/add")
    @Consumes("application/json")
    @Produces("text/plain")
    public String addContact(final Contact contact ) {

        if (contact == null){
            return "Contact is empty";
        }

        if(indexContact(contact)){
            return SUCCESS_MSG;
        }else {
            return FAIL_MSG;
        }
    }

    // stores new contact
    private boolean indexContact(final Contact contact) {
        return true;
    }
}
