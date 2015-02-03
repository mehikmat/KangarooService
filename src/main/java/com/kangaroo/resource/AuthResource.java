package com.kangaroo.resource;

import com.kangaroo.model.Auth;
import com.kangaroo.model.Response;
import com.kangaroo.util.HSqlDbConnection;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.kangaroo.util.Constants.*;

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
    @Produces("application/json")
    public Response respondAsReady() {
        return new Response("Service is up!!!");
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
    @Path("/validate")
    @Consumes("application/json")
    @Produces("application/json")
    public Response authCustomer(final Auth auth) {

        if (auth == null){
            return new Response("customer id or password is empty");
        }

        if (validateUser(auth)){
            return new Response(AUTHENTICATED);
        }else {
            return new Response(NOT_AUTHENTICATED);
        }
    }

    @POST
    @Path("/add")
    @Consumes("application/json")
    @Produces("application/json")
    public Response createCustomer(Auth auth){
        if (auth == null){
            return new Response("customer id or password is empty");
        }

        if (addCustomer(auth)){
            return new Response(SUCCESS_MSG);
        }else {
           return new Response(FAIL_MSG);
        }
    }

    // check if user exists in database
    private boolean validateUser(Auth auth) {
        String selectCustomer = "select * from auth where customerId=? and password=?";
        PreparedStatement statement = HSqlDbConnection.getStatement(selectCustomer);
        try {
            statement.setString(1,auth.getCustomerId());
            statement.setString(2,auth.getPassword());
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    // inset new customer
    private boolean addCustomer(Auth auth){
        String insertCustomer = "insert into auth (customerId,password) " + "values(?,?)";
        PreparedStatement statement = HSqlDbConnection.getStatement(insertCustomer);
        try {
            statement.setString(1,auth.getCustomerId());
            statement.setString(2,auth.getPassword());
            return !statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }
}