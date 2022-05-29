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

    public String getAccountPinHash() {
        return this.accountPinHash;
    }

    public String getUUID() {
        return this.uuid;
    }

    public void addAccount(Account acct) {
        this.customerAccounts.add(acct);
    }
    

    

}
