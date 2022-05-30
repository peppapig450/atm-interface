package com.java.atmInterface;

import java.util.ArrayList;

// Class for creating a bank account object
public class Account {
    
    private static String name;
    private String uuid;

    private Customer accountHolder; // customer 
    private ArrayList<AccountTransaction> transactions; // list of transactions for this account

    // constructor for the account class
    public Account(String name, Customer holder, Bank bank) {
        // set the name of the account and the account holder
        Account.name = name;
        this.accountHolder = holder; 

        // get new account UUID
        this.uuid = bank.getNewAccountUUID();

        // initialize transactions arraylist
        this.transactions = new ArrayList<AccountTransaction>();
    }

    // get method for account Unique ID
    public String getUUID() {
        return this.uuid;
    }

    // add transaction to list of transactions
    public void addTransaction(double amount) {
        AccountTransaction newTrans = new AccountTransaction(amount, this);
        transactions.add(newTrans);
    }

    // overload previous method to include the note
    public void addTransaction(double amount, String note) {
        AccountTransaction newTrans = new AccountTransaction(amount, note, this); 
        transactions.add(newTrans);
    }

    // get balance of the account by adding the values of it's transactions
    public double getBalance() {
        double balance = 0;
        for (AccountTransaction tran : transactions) {
            balance += tran.getAmount();
        }

        return balance;
    }

    // get method for the name of the account
    public static String getName() {
        return name;
    }

    // print out summary  for the account
    public String getSummaryLine() {
        double balance = getBalance();

        if (balance >= 0) {
            return String.format("%s : $%.02f : %s", uuid, balance, name); // format line as normal
        } else {
            return String.format("%s : $(%.02f) : %s", uuid, balance, name); // if value is negative
        }
    }

    // prints out the accounts transactions
    public void printTransactionHistory() {
        System.out.printf("\nTransaction history for account %s\n", uuid);
        for (int i = transactions.size() - 1; i >= 0; i--) {
            System.out.println(transactions.get(i).getSummaryLine());
        }
        System.out.println();
    }
}