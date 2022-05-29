package com.java.atmInterface;

import com.java.atmInterface.security.PinHash;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
public class Customer {
    private String firstName;
    private String lastName;
    private String uuid = "";

    private String accountPinHash;
    private ArrayList<Account> customerAccounts;

    // create new customer with given name, pin, and bank branch
    public Customer(String firstName, String lastName, String pin, Bank bank) throws NoSuchAlgorithmException, InvalidKeySpecException {

        // set user's name
        this.firstName = firstName;
        this.lastName = lastName;

        // store the pin's salted PBKDF2 hash instead of original, for security reasons
        this.accountPinHash = PinHash.generateHash(pin);

        // get a new, unique universal unique ID for the user
        this.uuid = bank.getNewUserUUID();

        // create empty list of accounts
        customerAccounts = new ArrayList<Account>();

        // print log message
        System.out.printf("New user %s, %s with ID %s created.\n",
                lastName, firstName, uuid);
    }

    // get the hash of the account's pin
    public String getAccountPinHash() {
        return this.accountPinHash;
    }

    // get the accounts UUID
    public String getUUID() {
        return this.uuid;
    }

    // add an account to the list
    public void addAccount(Account acct) {
        this.customerAccounts.add(acct);
    }
    
    // get number of customer accounts
    public int numAccounts() {
        return this.customerAccounts.size();
    }

    // get the balance of the account at the specified index
    public double getAccountBalance(int acctIndex) {
        return this.customerAccounts.get(acctIndex).getBalance();
    }

    // get the UUID of the account at the specified index
    public String getAccountUUID(int acctIndex) {
        return this.customerAccounts.get(acctIndex).getUUID();
    }

    // display the history of the account at the specified index
    public void displayAccountHistory(int acctIndex) {
        customerAccounts.get(acctIndex).printTransactionHistory();
    }

    // add a transaction for the account a tthe 
    public void addAccountTransaction(int acctIndex, double amount, String memo) {
        customerAccounts.get(acctIndex).addTransaction(amount, memo);
    }



    

}
