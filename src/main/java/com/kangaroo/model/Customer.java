package com.kangaroo.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * JAX-RS supports automatic creation of JSON and XML from java classes and vice-versa via JAXB
 * Java class to XML/JSON ---> marshaling
 * XML/JSON to java class ---> unmarshaling
 * JAX-RS supports an automatic mapping from JAXB annotated class to
 * XML and JSON.
 *
 * @author Hikmat Dhamee
 * @email me.hemant.available@gmail.com
 */
@XmlRootElement(name = "customer")
//If you want you can define the order in which the fields are written
@XmlType(propOrder = { "customerId", "customerName"})
public class Customer implements Serializable {

    @XmlElement(name = "customerId")
    private String customerId;

    @XmlElement(name = "customerName")
    private String customerName;

    public Customer() {}

    public Customer(String customerId, String customerName) {
        this.customerId = customerId;
        this.customerName = customerName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
