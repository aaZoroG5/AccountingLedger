package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;

public class Transaction {

    //create properties, NOTE: Added the final modifier to make the properties immutable
    private final String vendor;
    private final LocalDate date;
    private final String description;
    private final double amount;
    private final LocalTime time;

    //generate constructor for Transaction objects
    public Transaction(String vendor, LocalDate date, String description, double amount, LocalTime time) {
        this.vendor = vendor;
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.time = time;
    }

    //generate getter methods, NOTE: no setter methods were generated because we don't want to accidently edit the ledger data
    public String getVendor() {
        return vendor;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public LocalTime getTime() {
        return time;
    }
}
