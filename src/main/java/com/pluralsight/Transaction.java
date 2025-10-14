package com.pluralsight;

public class Transaction {

    //create properties
    private String vendor;
    private String date;
    private String description;
    private double amount;

    //generate constructor for Transaction objects
    public Transaction(String vendor, String date, String description, double amount) {
        this.vendor = vendor;
        this.date = date;
        this.description = description;
        this.amount = amount;
    }

    //generate getter methods, NOTE: no setter methods were generated because we don't want to accidently edit the ledger data
    public String getVendor() {
        return vendor;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }
}
