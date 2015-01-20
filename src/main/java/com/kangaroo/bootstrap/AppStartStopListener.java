package com.kangaroo.bootstrap;

import com.kangaroo.utility.DBConnection;
import com.kangaroo.utility.HSqlDbConnection;

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
        System.out.println("database created!");
        // create tables
        try {
            Statement statement = connection.createStatement();
            createTables(statement);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void createTables(Statement statement) throws SQLException {
        String demoCustomer = "insert into auth (customerId,password) " + "values('demo','demo')";

        String tableAuth = "create table if not exists auth (" +
                          "id INTEGER IDENTITY," +
                          "customerId varchar ( 10 )," +
                          "password varchar(10)" +
                       ")";
        String tableCustomer = "create table if not exists auth (" +
                          "id INTEGER IDENTITY," +
                          "customerId varchar ( 10 )," +
                          "customerName varchar(10)" +
                       ")";
        String tableContact = "create table if not exists auth (" +
                          "id INTEGER IDENTITY," +
                          "customerId varchar ( 10 )," +
                          "contactName varchar(10)," +
                          "contactNumber varchar(10)" +
                       ")";

        statement.execute(tableAuth);
        statement.execute(demoCustomer);
        System.out.println("Table auth created !");

        statement.execute(tableCustomer);
        System.out.println("Table customer created !");

        statement.execute(tableContact);
        System.out.println("Table contact created !");
    }

    private void destroyApp(){
    }
}

