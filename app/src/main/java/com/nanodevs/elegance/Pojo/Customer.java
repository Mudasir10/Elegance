package com.nanodevs.elegance.Pojo;

public class Customer  {


    private long mapNo;
    private String customerName;
    private String customerContact;
    private String suitType;
    private String clothType;

    private String stitchType;
    private String customerMeasurements;
    private String suitDescription;
    private boolean suitStatus;


    public Customer() {
    }

    public Customer(long mapNo, String customerName, String customerContact, String customerMeasurements,
                    String suitDescription, String suitType, String clothType, String stitchType, boolean suitStatus) {
        this.mapNo = mapNo;
        this.customerName = customerName;
        this.customerContact = customerContact;
        this.customerMeasurements = customerMeasurements;
        this.suitDescription = suitDescription;
        this.suitType = suitType;
        this.clothType = clothType;
        this.stitchType = stitchType;
        this.suitStatus = suitStatus;
    }


    public long getMapNo() {
        return mapNo;
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

    public String getSuitType() {
        return suitType;
    }

    public String getClothType() {
        return clothType;
    }

    public String getStitchType() {
        return stitchType;
    }

    public boolean isSuitStatus() {
        return suitStatus;
    }


}
