package com.kangaroo.resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import com.kangaroo.model.User;

import com.kangaroo.utility.Connector;
 
@Path("/auth")
public class AuthUserResource {
	 
	    private final static String USERNAME= "username";
	    private final static String PASSWORD= "password";
	    String result;
	   
	    private User user = new User("Hemant Dhmai", "9848751308");
	     
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
	    public String respondAsReady() {
	        return "Demo service is ready!";
	    }
	 
	    @GET
	    @Path("sample")
	    @Produces(MediaType.APPLICATION_JSON)
	    public User getSamplePerson() {
	         
	        System.out.println("Returning sample person: " + user.getUsername() + " " +user.getPassword());
	         
	        return user;
	    }
	         
	    // Use data from the client source to create a new Person object, returned in JSON format. 
	    @POST
	    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	    @Produces(MediaType.APPLICATION_JSON)
	    public String postPerson(
	            MultivaluedMap<String, String> userParams
	            ) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
	         
	        String username =userParams.getFirst(USERNAME);
	        String password = userParams.getFirst(PASSWORD);	        
	        
	        verifyuser(username,password);	       
	        return result;	    
	}

		private void verifyuser(String username, String password) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
			
			Connection con=new Connector().getDbConnection();
			Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs=stmt.executeQuery("select user_name pass_word from user where user_name="+"'"+username+"'"+"AND"+" "+"pass_word="+"'"+password+"'");
			if(!rs.next())
			{
	           	result="invalid";

                
			}
			else
			{
				result="ok";
			}
						
		   	rs.close();
		   	
	         stmt.close();
	         con.close();
	         return;
	      

		}
}
