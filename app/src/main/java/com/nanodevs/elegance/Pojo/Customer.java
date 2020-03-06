package com.nanodevs.elegance.Pojo;

import java.util.HashMap;
import java.util.Map;

public class Customer  {


    private long customerSerial;
    private String customerName;
    private String customerContact;


    public Customer() {

    }

    public Customer(long customerSerial, String customerName, String customerContact
                    ) {

        this.customerSerial = customerSerial;
        this.customerName = customerName;
        this.customerContact = customerContact;

    }



    public Map<String,Object> toCustomerMap(){

        HashMap<String,Object> result=new HashMap<>();
        result.put("customerSerial",customerSerial);
        result.put("customerName",customerName);
        result.put("customerContact",customerContact);
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




    public void setCustomerSerial(long customerSerial) {
        this.customerSerial = customerSerial;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }


}
