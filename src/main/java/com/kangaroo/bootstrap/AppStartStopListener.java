package com.kangaroo.bootstrap;

import com.kangaroo.util.DBConnection;
import com.kangaroo.util.HSqlDbConnection;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Bootstrap class for web app.
 * This is called only once at startup of a web app and
 * only once at the end of web app
 * Drawback again; there is one context per "web application" per Java Virtual Machine.
 *
 * @author Hikmat Dhamee
 * @email me.hemant.available@gmail.com
 */
public class AppStartStopListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        initApp();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        destroyApp();
    }

    private void initApp(){
        // init database parameters
        DBConnection dbConnection = new HSqlDbConnection();
        // open database
        Connection connection = dbConnection.getConnection();
        System.out.println("database k_contact opened!");
        // create tables
        try {
            Statement statement = connection.createStatement();
            createTables(statement);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void createTables(Statement statement) throws SQLException {
        String demoCustomer = "insert into customer (customerId,password) " + "values('demo','demo')";
        String demoContact = "insert into contact (customerId,contactName,contactNumber) " + "values('demo','demo','1010101010')";
        String demoAuth = "insert into auth (customerId,password) " + "values('demo','demo')";

        // this is for web admin
        String tableAuth = "create table if not exists auth (" +
                "id INTEGER IDENTITY," +
                "customerId varchar ( 10 )," +
                "password varchar(10)" +
                ")";

        // this is for mobile login
        String tableCustomer = "create table if not exists customer (" +
                "id INTEGER IDENTITY," +
                "customerId varchar ( 10 )," +
                "password varchar(20)" +
                ")";
        String tableContact = "create table if not exists contact (" +
                "id INTEGER IDENTITY," +
                "customerId varchar ( 10 )," +
                "contactName varchar(20)," +
                "contactNumber varchar(20)" +
                ")";

        if(!statement.execute(tableAuth)  && !statement.execute(demoAuth)){
            System.out.println("Table auth created !");
        }

        if(!statement.execute(tableCustomer) && !statement.execute(demoCustomer)){
            System.out.println("Table customer created !");
        }

        if(!statement.execute(tableContact) && !statement.execute(demoContact)){
            System.out.println("Table contact created !");
        }
    }

    private void destroyApp(){
    }
}

