package com.nanodevs.elegance.Pojo;

public class ShowOrder {

    private String Cus_Id;
    private long OrderCount;

    public ShowOrder() {
    }

    public ShowOrder(String cus_Id, long orderCount) {
        Cus_Id = cus_Id;
        OrderCount = orderCount;
    }

    public String getCus_Id() {
        return Cus_Id;
    }

    public void setCus_Id(String cus_Id) {
        Cus_Id = cus_Id;
    }

    public long getOrderCount() {
        return OrderCount;
    }

    public void setOrderCount(long orderCount) {
        OrderCount = orderCount;
    }
}
