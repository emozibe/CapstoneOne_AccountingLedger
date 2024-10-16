/*
 * Transaction class represents a single financial transaction,
 * including date, time, description, vendor, and amount
 */

package com.ps;

public class Transaction {

    // Transaction attributes
    private String date;
    private String time;
    private String description;
    private String vendor;
    private double amount;

    // Constructor for initializing a Transaction object with specific attributes
    public Transaction(String date,
                       String time,
                       String description,
                       String vendor,
                       double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    // Getters and Setters for each attribute

    public String getDate() {
        return date;
    } public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    } public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    } public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    } public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public double getAmount() {
        return amount;
    } public void setAmount(double amount) {
        this.amount = amount;
    }

    // Returns a string representation of the transaction,
    // which is useful for logging and debugging purposes
    @Override
    public String toString() {
        return "Transaction{" +
                "date=" + date +
                ", time=" + time +
                ", description='" + description + '\'' +
                ", vendor='" + vendor + '\'' +
                ", amount=" + amount +
                '}';
    }
}