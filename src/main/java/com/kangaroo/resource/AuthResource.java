package com.kangaroo.resource;

import com.kangaroo.model.Auth;
import com.kangaroo.utility.DBConnector;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.kangaroo.utility.Constants.*;

@Path("/auth")
public class AuthResource {
    // Default response
    private Auth demoCustomer = new Auth("demo","demo");

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

    public AuthResource() {}

    // Basic "is the service running" test
    @GET
    @Produces("text/plain")
    public String respondAsReady() {
        return "Service is up... :)";
    }

    @GET
    @Path("/demo")
    @Produces("application/json")
    public Auth getDemoCustomer(){
        return demoCustomer;
    }

    /* Receive auth information from client app and send response
     as AUTHENTICATED or NOT_AUTHENTICATED
     client needs to post json as string, posted json string will be converted into
     “Auth” object automatically.*/
    @POST
    @Path("/check")
    @Consumes("application/json")
    @Produces("application/json")
    public String authCustomer(final Auth auth) throws SQLException, ClassNotFoundException,
            InstantiationException, IllegalAccessException {

        if (auth == null){
            return "customer id or password is empty";
        }

        if (validateUser(auth)){
            return AUTHENTICATED;
        }else {
            return NOT_AUTHENTICATED;
        }
    }

    @POST
    @Path("create")
    @Consumes("application/json")
    @Produces("text/plain")
    public String createCustomer(Auth auth) throws SQLException, ClassNotFoundException,
            InstantiationException, IllegalAccessException {

        if (auth == null){
            return "customer id or password is empty";
        }

        if (addCustomer(auth)){
            return SUCCESS_MSG;
        }else {
           return FAIL_MSG;
        }
    }

    // check if user exists in database
    private boolean validateUser(Auth auth) {
        return true;
    }

    // index new user
    private boolean addCustomer(Auth auth){
        return true;
    }
}