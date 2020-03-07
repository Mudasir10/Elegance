package com.nanodevs.elegance.Pojo;

import java.util.HashMap;
import java.util.Map;

public class Cart {


    private long boskiQty;
    private long cottonQty;
    private long khaadiQty;
    private long karandiQty;
    private long lilanQty;
    private long wWearQty;
    private String suitType;

    public Cart( long boskiQty, long cottonQty, long khaadiQty, long karandiQty, long lilanQty, long wWearQty,String suitType) {
        this.boskiQty = boskiQty;
        this.cottonQty = cottonQty;
        this.khaadiQty = khaadiQty;
        this.karandiQty = karandiQty;
        this.lilanQty = lilanQty;
        this.wWearQty = wWearQty;
        this.suitType=suitType;
    }

    public Cart() {
    }

    public Map<String,Object> toCartMap(){

        HashMap<String,Object> result=new HashMap<>();
        result.put("boskiQty",boskiQty);
        result.put("cottonQty",cottonQty);
        result.put("khaadiQty",khaadiQty);
        result.put("karandiQty",karandiQty);
        result.put("lilanQty",lilanQty);
        result.put("wWearQty",wWearQty);
        result.put("suitType",suitType);

        return result;
    }



    public long getBoskiQty() {
        return boskiQty;
    }

    public void setBoskiQty(long boskiQty) {
        this.boskiQty = boskiQty;
    }

    public long getCottonQty() {
        return cottonQty;
    }

    public void setCottonQty(long cottonQty) {
        this.cottonQty = cottonQty;
    }

    public long getKhaadiQty() {
        return khaadiQty;
    }

    public void setKhaadiQty(long khaadiQty) {
        this.khaadiQty = khaadiQty;
    }

    public long getKarandiQty() {
        return karandiQty;
    }

    public void setKarandiQty(long karandiQty) {
        this.karandiQty = karandiQty;
    }

    public long getLilanQty() {
        return lilanQty;
    }

    public void setLilanQty(long lilanQty) {
        this.lilanQty = lilanQty;
    }

    public long getwWearQty() {
        return wWearQty;
    }

    public void setwWearQty(long wWearQty) {
        this.wWearQty = wWearQty;
    }


    public String getSuitType() {
        return suitType;
    }

    public void setSuitType(String suitType) {
        this.suitType = suitType;
    }
}
