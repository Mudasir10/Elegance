package com.nanodevs.elegance.Pojo;

import java.util.HashMap;
import java.util.Map;

public class ClothOrder {

    private long customerSerial;
    private String customerName;
    private String customerContact;
    private String customerMeasurements;
    private String suitDescription;

    private String clothType;
    private String stitchType;
    private String suitType;
    private String suitQuantity;

    private String orderDate;
    private String expectedDate;
    private boolean suitStatus;
    private long suitAdvancePayment;
    private long suitAdvanceRemainingPayment;
    private long balance;
    private long remainingBalance;



    public ClothOrder() {
    }


   /* public Map<String,Object> toClothStitchMap(){

        HashMap<String,Object> result=new HashMap<>();
        result.put("customerSerial",customerSerial);
        result.put("customerName",customerName);
        result.put("customerContact",customerContact);
        result.put("suitType",suitType);
        result.put("clothType",clothType);
        result.put("stitchType",stitchType);
        result.put("suitStatus",suitStatus);
        result.put("suitAdvancePayment",suitAdvancePayment);
        result.put("suitRemainingPayment",suitRemainingPayment);
        result.put("clothQuantity",suitQuantity);

        return result;
    }


    public ClothOrder(long customerSerial, String customerName, String customerContact,
                      String suitType, String clothType, String stitchType,
                      String clothQuantity, boolean suitStatus, long suitAdvancePayment,
                      long suitRemainingPayment) {

        this.customerSerial = customerSerial;
        this.customerName = customerName;
        this.customerContact = customerContact;
        this.suitType = suitType;
        this.clothType = clothType;
        this.stitchType = stitchType;
        this.suitQuantity = clothQuantity;
        this.suitStatus = suitStatus;
        this.suitAdvancePayment = suitAdvancePayment;
        this.suitRemainingPayment = suitRemainingPayment;
    }

    public long getCustomerSerial() {
        return customerSerial;
    }

    public void setCustomerSerial(long customerSerial) {
        this.customerSerial = customerSerial;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public String getSuitType() {
        return suitType;
    }

    public void setSuitType(String suitType) {
        this.suitType = suitType;
    }

    public String getClothType() {
        return clothType;
    }

    public void setClothType(String clothType) {
        this.clothType = clothType;
    }

    public String getStitchType() {
        return stitchType;
    }

    public void setStitchType(String stitchType) {
        this.stitchType = stitchType;
    }

    public String getClothQuantity() {
        return suitQuantity;
    }

    public void setClothQuantity(String clothQuantity) {
        this.suitQuantity = clothQuantity;
    }

    public boolean isSuitStatus() {
        return suitStatus;
    }

    public void setSuitStatus(boolean suitStatus) {
        this.suitStatus = suitStatus;
    }

    public long getSuitAdvancePayment() {
        return suitAdvancePayment;
    }

    public void setSuitAdvancePayment(long suitAdvancePayment) {
        this.suitAdvancePayment = suitAdvancePayment;
    }

    public long getSuitRemainingPayment() {
        return suitRemainingPayment;
    }

    public void setSuitRemainingPayment(long suitRemainingPayment) {
        this.suitRemainingPayment = suitRemainingPayment;
    }*/
}
