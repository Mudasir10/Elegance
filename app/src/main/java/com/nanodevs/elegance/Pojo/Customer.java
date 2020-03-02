package com.nanodevs.elegance.Pojo;

import java.util.HashMap;
import java.util.Map;

public class Customer  {


    private long customerSerial;
    private String customerName;
    private String customerContact;
    private String customerMeasurements;
    private String suitDescription;




    public Customer() {
    }

    public Customer(long customerSerial, String customerName, String customerContact,
                    String customerMeasurements, String suitDescription) {

        this.customerSerial = customerSerial;
        this.customerName = customerName;
        this.customerContact = customerContact;
        this.customerMeasurements = customerMeasurements;
        this.suitDescription = suitDescription;
    }



    public Map<String,Object> toCustomerMap(){

        HashMap<String,Object> result=new HashMap<>();
        result.put("customerSerial",customerSerial);
        result.put("customerName",customerName);
        result.put("customerContact",customerContact);
        result.put("customerMeasurements",customerMeasurements);
        result.put("suitDescription",suitDescription);

        return result;
    }



    public long getCustomerSerial() {
        return customerSerial;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public String getCustomerMeasurements() {
        return customerMeasurements;
    }

    public String getSuitDescription() {
        return suitDescription;
    }


    public void setCustomerSerial(long customerSerial) {
        this.customerSerial = customerSerial;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public void setCustomerMeasurements(String customerMeasurements) {
        this.customerMeasurements = customerMeasurements;
    }

    public void setSuitDescription(String suitDescription) {
        this.suitDescription = suitDescription;
    }
}
