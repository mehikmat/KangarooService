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
@XmlRootElement(name = "contact")
//If you want you can define the order in which the fields are written
@XmlType(propOrder = { "customerId","contactName", "contactNumber"})
public class Contact implements Serializable {

    @XmlElement(name = "customerId")
    private String customerId;

    @XmlElement(name = "contactName")
    private String contactName;

    @XmlElement(name = "contactNumber")
    private String contactNumber;

    public Contact() {}

    public Contact(String customerId ,String contactName, String contactNumber) {
        this.setCustomerId(customerId);
        this.setContactName(contactName);
        this.setContactNumber(contactNumber);
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
