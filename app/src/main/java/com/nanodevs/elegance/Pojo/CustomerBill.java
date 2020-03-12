package com.nanodevs.elegance.Pojo;

import java.util.HashMap;
import java.util.Map;

public class CustomerBill {

    private String customerName;
    private long customerSerial;
    private String customerContact;


    private String orderDate;
    private String expectedDate;
    private boolean suitStatus;
    private long totalBill;
    private long discount;
    private long suitAdvancePayment;
    private long balance;
    private long remainingBalance;

    public CustomerBill(long customerSerial, String customerName, String customerContact, String orderDate,
                        String expectedDate, boolean suitStatus,
                        long totalBill, long discount, long suitAdvancePayment, long balance, long remainingBalance) {
        this.customerSerial = customerSerial;
        this.customerName = customerName;
        this.customerContact = customerContact;
        this.orderDate = orderDate;
        this.expectedDate = expectedDate;
        this.suitStatus = suitStatus;
        this.totalBill = totalBill;
        this.discount = discount;
        this.suitAdvancePayment = suitAdvancePayment;
        this.balance = balance;
        this.remainingBalance = remainingBalance;

    }

    public CustomerBill() {
    }

    public Map<String, Object> completeCustomerBillWithAdvanceAndDiscountMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("customerSerial", customerSerial);
        result.put("customerName", customerName);
        result.put("customerContact", customerContact);
        result.put("suitStatus", suitStatus);
        result.put("orderDate", orderDate);
        result.put("expectedDate", expectedDate);
        result.put("totalBill", totalBill);
        result.put("discount", discount);
        result.put("suitAdvancePayment", suitAdvancePayment);
        result.put("balance", balance);
        result.put("remainingBalance", remainingBalance);
        return result;
    }

    public Map<String, Object> withoutAdvanceAndDiscountMapBill() {
        HashMap<String, Object> result2 = new HashMap<>();
        result2.put("customerSerial", customerSerial);
        result2.put("customerName", customerName);
        result2.put("customerContact", customerContact);
        result2.put("suitStatus", suitStatus);
        result2.put("orderDate", orderDate);
        result2.put("expectedDate", expectedDate);
        result2.put("totalBill", totalBill);
        result2.put("balance", balance);
        result2.put("remainingBalance", remainingBalance);
        return result2;

    }


    public Map<String, Object> withAdvanceMapBill() {
        HashMap<String, Object> result3 = new HashMap<>();
        result3.put("customerSerial", customerSerial);
        result3.put("customerName", customerName);
        result3.put("customerContact", customerContact);
        result3.put("suitStatus", suitStatus);
        result3.put("orderDate", orderDate);
        result3.put("expectedDate", expectedDate);
        result3.put("totalBill", totalBill);
        result3.put("suitAdvancePayment", suitAdvancePayment);
        result3.put("balance", balance);
        result3.put("remainingBalance", remainingBalance);
        return result3;
    }


    public Map<String, Object> withDiscountMapBill() {
        HashMap<String, Object> result4 = new HashMap<>();
                result4.put("customerSerial", customerSerial);
                result4.put("customerName", customerName);
                result4.put("customerContact", customerContact);
                result4.put("suitStatus", suitStatus);
                result4.put("orderDate", orderDate);
                result4.put("expectedDate", expectedDate);
                result4.put("totalBill", totalBill);
                result4.put("discount", discount);
                result4.put("balance", balance);
                result4.put("remainingBalance", remainingBalance);
        return result4;
    }



    public void  setMethodWithoutAdvanceAndDiscount(long customerSerial, String customerName, String customerContact, String orderDate,
                                           String expectedDate, boolean suitStatus,
                                           long totalBill, long balance, long remainingBalance){


        this.customerSerial = customerSerial;
        this.customerName = customerName;
        this.customerContact = customerContact;
        this.orderDate = orderDate;
        this.expectedDate = expectedDate;
        this.suitStatus = suitStatus;
        this.totalBill = totalBill;
        this.balance = balance;
        this.remainingBalance = remainingBalance;

    }

    public void  setMethodWithAdvanceOnly(long customerSerial, String customerName, String customerContact, String orderDate,
                                String expectedDate, boolean suitStatus,
                                long totalBill, long suitAdvancePayment, long balance, long remainingBalance){


        this.customerSerial = customerSerial;
        this.customerName = customerName;
        this.customerContact = customerContact;
        this.orderDate = orderDate;
        this.expectedDate = expectedDate;
        this.suitStatus = suitStatus;
        this.totalBill = totalBill;
        this.suitAdvancePayment = suitAdvancePayment;
        this.balance = balance;
        this.remainingBalance = remainingBalance;

    }

  public void  setMethodWithDiscountOnly(long customerSerial, String customerName, String customerContact, String orderDate,
                               String expectedDate, boolean suitStatus,
                               long totalBill, long discount, long balance, long remainingBalance){

      this.customerSerial = customerSerial;
      this.customerName = customerName;
      this.customerContact = customerContact;
      this.orderDate = orderDate;
      this.expectedDate = expectedDate;
      this.suitStatus = suitStatus;
      this.totalBill = totalBill;
      this.discount = discount;
      this.balance = balance;
      this.remainingBalance = remainingBalance;

    }


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public long getCustomerSerial() {
        return customerSerial;
    }

    public void setCustomerSerial(long customerSerial) {
        this.customerSerial = customerSerial;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(String expectedDate) {
        this.expectedDate = expectedDate;
    }

    public boolean isSuitStatus() {
        return suitStatus;
    }

    public void setSuitStatus(boolean suitStatus) {
        this.suitStatus = suitStatus;
    }

    public long getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(long totalBill) {
        this.totalBill = totalBill;
    }

    public long getDiscount() {
        return discount;
    }

    public void setDiscount(long discount) {
        this.discount = discount;
    }

    public long getSuitAdvancePayment() {
        return suitAdvancePayment;
    }

    public void setSuitAdvancePayment(long suitAdvancePayment) {
        this.suitAdvancePayment = suitAdvancePayment;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public long getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(long remainingBalance) {
        this.remainingBalance = remainingBalance;
    }
}

