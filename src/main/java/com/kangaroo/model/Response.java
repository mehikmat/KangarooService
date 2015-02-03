package com.kangaroo.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * @author Hikamt Dhamee
 * @email me.hemant.available@gmail.com
 */
@XmlRootElement(name = "response")
//If you want you can define the order in which the fields are written
@XmlType(propOrder = { "status"})
public class Response implements Serializable {
    @XmlElement(name = "status")
    private String status = "";

    public Response() {}

    public Response(String status){
        this.setStatus(status);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
