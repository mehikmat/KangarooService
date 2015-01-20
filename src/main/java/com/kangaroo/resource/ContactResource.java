package com.kangaroo.resource;

import com.kangaroo.model.Contact;
import com.kangaroo.model.Customer;
import com.kangaroo.utility.HSqlDbConnection;
import jersey.repackaged.com.google.common.collect.Lists;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.kangaroo.utility.Constants.FAIL_MSG;
import static com.kangaroo.utility.Constants.SUCCESS_MSG;

@Path("/contact")
public class ContactResource {
    String result;

    private Contact demoContact = new Contact("01","Hemant Dhamee", "4739383873");

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

    // Use data from the client source to create a new Contact object, returned in JSON format.
    @POST
    @Path("/add")
    @Consumes("application/json")
    @Produces("text/plain")
    public String addContact(final Contact contact ) {

        if (contact == null){
            return "Contact is empty";
        }

        if(insertContact(contact)){
            return SUCCESS_MSG;
        }else {
            return FAIL_MSG;
        }
    }

     // Use data from the client source to get list of contact for particular customer.
    @POST
    @Path("/list")
    @Consumes("application/json")
    @Produces("application/json")
    public List<Contact> listContact(final Customer customer ) {

        if (customer == null){
            return null;
        }
        return getContactList(customer);
    }

    // get list of contacts
    private List<Contact> getContactList(Customer customer){
        String selectContact = "select * from contact where customerId=?";
        PreparedStatement statement = HSqlDbConnection.getStatement(selectContact);
        try {
            statement.setString(1, customer.getCustomerId());
            ResultSet resultSet = statement.executeQuery();
            List<Contact> list = new ArrayList<>();
            while (resultSet.next()){
                list.add(new Contact(customer.getCustomerId(),resultSet.getString("contactName"),
                        resultSet.getString("contactNumber")));
            }
            System.out.println("Returning>>> "+Lists.newArrayList(list));
            return Lists.newArrayList(list);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    // stores new contact
    private boolean insertContact(final Contact contact) {
        String insertContact = "insert into contact (customerId,contactName,contactNumber) " + "values(?,?,?)";
        PreparedStatement statement = HSqlDbConnection.getStatement(insertContact);
        try {
            statement.setString(1,contact.getCustomerId());
            statement.setString(2,contact.getContactName());
            statement.setString(3,contact.getContactNumber());
            return !statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }
}
