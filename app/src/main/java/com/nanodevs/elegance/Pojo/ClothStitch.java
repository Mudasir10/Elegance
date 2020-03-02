package com.nanodevs.elegance.Pojo;

import java.util.HashMap;
import java.util.Map;

public class ClothStitch {


    private long customerSerial;
    private String customerName;
    private String suitType;
    private String clothType;
    private String stitchType;
    private boolean suitStatus;
    private String clothQuantity;

    public ClothStitch() {
    }


    public ClothStitch(long customerSerial, String customerName, String suitType, String clothType,
                       String stitchType, boolean suitStatus, String clothQuantity) {
        this.customerSerial = customerSerial;
        this.customerName = customerName;
        this.suitType = suitType;
        this.clothType = clothType;
        this.stitchType = stitchType;
        this.suitStatus = suitStatus;
        this.clothQuantity = clothQuantity;

    }



    public Map<String,Object> toClothStitchMap(){

        HashMap<String,Object> result=new HashMap<>();
        result.put("customerSerial",customerSerial);
        result.put("customerName",customerName);
        result.put("suitType",suitType);
        result.put("clothType",clothType);
        result.put("stitchType",stitchType);
        result.put("suitStatus",suitStatus);
        result.put("clothQuantity",clothQuantity);

        return result;
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

    public boolean isSuitStatus() {
        return suitStatus;
    }

    public void setSuitStatus(boolean suitStatus) {
        this.suitStatus = suitStatus;
    }

    public String getClothQuantity() {
        return clothQuantity;
    }

    public void setClothQuantity(String clothQuantity) {
        this.clothQuantity = clothQuantity;
    }


}
