package com.kangaroo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MySqlDbConnection implements DBConnection {
    private String hostName;
    private String driverName;
    private String userName;
    private String passWord;

    private Connection dbConnection;

    public MySqlDbConnection() {
        ResourceBundle resource = ResourceBundle.getBundle("parameter");
        setHostName(resource.getString("host"));
        setDriverName(resource.getString("driver"));
        setUserName(resource.getString("username"));
        setPassWord(resource.getString("password"));
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Connection getConnection() {
        try {
            Class.forName(getDriverName()).newInstance();
            dbConnection = DriverManager.getConnection(getHostName(),
                    getUserName(), getPassWord());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {

            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return dbConnection;
    }
}
