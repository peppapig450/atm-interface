package com.java.atmInterface;

import java.util.Date;

// Class to handle account transactions so that we can store them in arraylists in the 
// account class
public class AccountTransaction {
    
    private double amount;
    private Date timestamp; // time and date of the transaction
    private String txtReference; // a note for the transaction
    private Account inAccount;

    // constructor to create an Account Transaction object
    public AccountTransaction(double amount, Account acc) {
        this.amount = amount;
        this.inAccount = acc;
        this.timestamp = new Date();
        this.txtReference = "";
    }

    // overload the AccountTransaction constructor
    public AccountTransaction(double amount, String txtReference, Account acc) {
        // call the single-argument constructor first
        this(amount, acc);

        this.txtReference = txtReference;
    }

    // get method for the double amount
    public double getAmount() {
        return this.amount;
    }

    // returns a string summarizing the text
    public String getSummaryLine() {
        if (amount >= 0) {
            return String.format("%s, $ %.02f : %s",
                            timestamp.toString(), amount, txtReference);
        } else {
            return String.format("%s, $ (%.02f) : %s",
                            timestamp.toString(), - amount, txtReference);
        }
    }   
}
