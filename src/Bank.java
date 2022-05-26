import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.naming.BinaryRefAddr;

// class for the Bank that contains all the customers and the accounts
public class Bank {

    private String name; // The name of the bank
    private ArrayList<Customer> usersList; // The bank's customers who hold accounts
    private ArrayList<Account> accountsList; // Accounts held with the bank

    // constructor for the bank class
    public Bank(String name) {
        this.name = name;

        usersList = new ArrayList<Customer>();
        accountsList = new ArrayList<Account>();
    }

    // generate a unique ID number for a account
    public String getNewUserUUID() {
        String uuid = "";
        Random rnd = new Random();
        int len = 6;
        boolean nonUnique = false;

        do {
            for (int i = 0; i < len; i++) {
                uuid += ((Integer) rnd.nextInt(10)).toString(); // generate the number
            }

            for (Customer user : userList) {
                if (uuid.compareTo(user.gettUUID()) == 0) { // if the number is unique we will use it
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique); // execute the loop until we get a unique ID number

        return uuid;
    }

    // create a new customer in the bank's system
    public Customer addUser(String firstName, String lastName, String pin) {

        // create a new Customer object and add it to the list
        Customer newUser = new Customer(firstName, lastName, pin, this);
        userList.add(newUser);

        // create a savings account for the new customer and add it to the list
        Account newAccount = new Account("Savings", newUser, this);
        newUser.addAccount(newAccount);
        accountsList.add(newAccount);

        return newUser;
    }

    // method to add account to the list of accounts
    public void addAccount(Account newAccount) {
        accountsList.add(newAccount);
    }

    // check if the entered credentials match an existing account, and if so login
    public Customer userLogin(String userID, String pin) {

        // search through list of users for matching userID
        for (Customer user : usersList) {
            // if the credentials match an existing user, return Customer object
            if (user.getUUID().compareTo(userID) == 0 && user.validatePin(pin)) {
                return user;
            }
        }

        // TODO: ADD EXCEPTION AND ASK USER TO CREATE AN ACCOUNT OR TRY AGAIN
        return null; // if credentials are incorrect return null
    }

    // get the name of the bank
    public String getName() {
        return this.name;
    }
}