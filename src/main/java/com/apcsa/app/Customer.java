package main.java.com.apcsa.app;

import java.util.ArrayList;

public class Customer {
    private String firstName;
    private String lastName;
    private String uuid = "";

    private String accountPinHash;
    private ArrayList<Account> customerAccounts;

    // create new customer with given name, pin, and bank branch
    public Customer(String firstName, String lastName, String pin, Bank bank)  {

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
    public void addAccountTransaction(int acctIndex, double amount, String note) {
        customerAccounts.get(acctIndex).addTransaction(amount, note);
    }

    // validate the accounts pin against the inputted one
    public boolean validatePin(String aPin)  {
        String checkHash;

        // check the pin hashes and use correct error handling
        try {
            // generate the hash of the entered pin to check against the account's pin hash
            checkHash = PinHash.generateHash(aPin);

            // if pin matches return true if it doesn't return false
            if (PinHash.validatePin(checkHash, accountPinHash)) {
                return true;
            } else {
                return false;
            }
            // handle errors
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    // print out a summary of all accounts for the customer
    public void printAccountsSummary() {

        System.out.printf("|\t\t\t\t\t\t\t|\n|%s's accounts summary\t\t\t\t|\n", firstName);
        System.out.println("|_______________________________________________________|");
        // iterate through the customer's accounts and print out their summaries
        for (int i = 0; i < customerAccounts.size(); i++) {
            System.out.printf("%d) %s\n", i+1, customerAccounts.get(i).getSummaryLine());
        }
        System.out.println();
    }

    // get the overdraft balance of the customers accounts
    public void getOverdraftBal() {
        // iterate through their accounts to print out the overdraft balance
        for (int i = 0; i < customerAccounts.size(); i++) {
            System.out.printf("%d) %s\n", i+1, customerAccounts.get(i).getBalance());
        }
    }
}
