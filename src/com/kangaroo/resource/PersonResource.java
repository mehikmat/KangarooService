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

import com.kangaroo.model.Person;
import com.kangaroo.utility.Connector;
 
@Path("/person")
public class PersonResource {
	 
	    private final static String NAME = "name";
	    private final static String NUMBER = "number";
	   
	    private Person person = new Person("Hemant Dhmai", "9848751308");
	     
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
	    public Person getSamplePerson() {
	         
	        System.out.println("Returning sample person: " + person.getName() + " " + person.getNumber());
	         
	        return person;
	    }
	         
	    // Use data from the client source to create a new Person object, returned in JSON format. 
	    @POST
	    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	    @Produces(MediaType.APPLICATION_JSON)
	    public Person postPerson(
	            MultivaluedMap<String, String> personParams
	            ) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
	         
	        String name =personParams.getFirst(NAME);
	        String number = personParams.getFirst(NUMBER);	 
	        String username=personParams.getFirst("username");
	        
	        inertToDatabase(name,number,username);	       
	        return person;	    
	}

		private void inertToDatabase(String name, String number,String username) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
			
			Connection con=new Connector().getDbConnection();
			Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs=stmt.executeQuery("select people_name mobile_no from people where people_name="+"'"+name+"'"+"AND"+" "+"mobile_no="+"'"+number+"'");
			if(!rs.next())
			{
	            PreparedStatement pstmt=con.prepareStatement("insert into people values(?,?,?)");
                pstmt.setString(1, name);
                pstmt.setString(2, number);
                pstmt.setString(3,username );
                if(pstmt.executeUpdate()>0)
                {
				person.setName("Saved"+" "+name);
				person.setNumber(number);
                }
                else
                {
                	person.setName("Problem Occured in inserting ");
                	person.setNumber(name+" "+number);
                	pstmt.close();
                }
			}
			else
			{
				person.setName("Already exists"+" "+name);
				person.setNumber(number);
			}
			
		   	rs.close();
		   	
	         stmt.close();
	         con.close();
	         return;
	      

		}
}
