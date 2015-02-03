package com.kangaroo.resource;

import com.kangaroo.model.Customer;
import com.kangaroo.model.Response;
import com.kangaroo.util.Constants;
import com.kangaroo.util.HSqlDbConnection;
import jersey.repackaged.com.google.common.collect.Lists;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.kangaroo.util.Constants.AUTHENTICATED;
import static com.kangaroo.util.Constants.NOT_AUTHENTICATED;

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
    @Produces("application/json")
    public Response responseAsReady() {
        return new Response("Service is up!");
    }

    // Basic "is the service running" test
    @GET
    @Path("/demo")
    @Produces("application/json")
    public Customer testService() {
        return demoCustomer;
    }

    // Use data from the client source to create a new Person object, returned in JSON format.
    @POST
    @Path("/add")
    @Consumes("application/json")
    @Produces("application/json")
    public Response addPerson( final Customer customer ) {

        // add new customer
        if (insertCustomer(customer)){
            return new Response(Constants.SUCCESS_MSG);
        }else {
            return new Response(Constants.FAIL_MSG);
        }
    }

    /* Receive auth information from client app and send response
     as AUTHENTICATED or NOT_AUTHENTICATED
     client needs to post json as string, posted json string will be converted into
     “Auth” object automatically.*/
    @POST
    @Path("/validate")
    @Consumes("application/json")
    @Produces("application/json")
    public Response authCustomer(final Customer auth) {

        if (auth == null){
            return new Response("customer id or password is empty");
        }

        if (validateUser(auth)){
            return new Response(AUTHENTICATED);
        }else {
            return new Response(NOT_AUTHENTICATED);
        }
    }

    // Use data from the client source to get list of contact for particular customer.
    @GET
    @Path("/list")
    @Produces("application/json")
    public List<Customer> listContact() {
       return getCustomerList();
    }

    // get list of customers
    private List<Customer> getCustomerList(){
        String selectContact = "select * from customer";
        PreparedStatement statement = HSqlDbConnection.getStatement(selectContact);
        try {
            ResultSet resultSet = statement.executeQuery();
            List<Customer> list = new ArrayList<>();
            while (resultSet.next()){
                list.add(new Customer(resultSet.getString(Constants.CUSTOMER_ID),
                        resultSet.getString(Constants.PASSWORD)));
            }
            return Lists.newArrayList(list);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    // add new customer
    private boolean insertCustomer(final  Customer customer) {
        String insertCustomer = "insert into customer (customerId,password) " + "values(?,?)";
        PreparedStatement statement = HSqlDbConnection.getStatement(insertCustomer);
        try {
            statement.setString(1,customer.getCustomerId());
            statement.setString(2,customer.getPassword());
            return !statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    // check if user exists in database
    private boolean validateUser(Customer auth) {
        String selectCustomer = "select * from customer where customerId=? and password=?";
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
}
