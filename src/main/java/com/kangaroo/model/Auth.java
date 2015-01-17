package com.kangaroo.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * @author Hikamt Dhamee
 * @email me.hemant.available@gmail.com
 */
@XmlRootElement(name = "auth")
//If you want you can define the order in which the fields are written
@XmlType(propOrder = { "customerId", "password"})
public class Auth implements Serializable {
    @XmlElement(name = "customerId")
    private String customerId ="";
    @XmlElement(name = "password")
    private String password = "";

    public Auth() {}

    public Auth(String customerId, String password) {
        this.customerId = customerId;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
