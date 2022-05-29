package com.java.atmInterface;

import java.util.Scanner;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.io.File;
import java.io.FileNotFoundException;

public class ATM implements Overdraft {
    public static void main(String[] args) throws FileNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException {
        Scanner sc = new Scanner(System.in);
        Bank theBank = new Bank("Golden Horizon Bank");
        Customer defaultUser = theBank.addUser("Harry", "Patterson", "0000");

        Account newAccount = new Account("Current Account", defaultUser, theBank);
        defaultUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        Account newCreditAccount = new CreditAccount("Credit Account", defaultUser, theBank, 1000);
        defaultUser.addAccount(newCreditAccount);
        theBank.addAccount(newCreditAccount);

        Customer overDraftCustomer = theBank.addUser("Elijah", "Waterson", "1234");

        Account newCreditAccount2 = new CreditAccount("Credit Account", overDraftCustomer, theBank, 1000);
        overDraftCustomer.addAccount(newCreditAccount2);
        theBank.addAccount(newCreditAccount2);

        Customer user;

        // run loop until program closes or is quit
        while (true) {
            // stay in loigin prompt until successful login
            user = ATM.mainMenuPrompt(theBank, sc);

            // stay in main menu until user quits
            ATM.customerMenu(user, sc);
        }
    }

        // print the ATM's login menu
        public static Customer mainMenuPrompt(Bank theBank, Scanner sc) {
            String userID;
            String pin;
            Customer authUser;

            // prompt user for user Id and pin combo until a correct one is entered
            do {
                System.out.println(" _____________________Cash Flows ATM _____________________");
                System.out.print("|\t\t\t\t\t\t\t| \n");
                System.out.printf("|\tWelcome to %s\t\t|\n", theBank.getName());
                System.out.print("|\t\t\t\t\t\t\t| \n");
                System.out.println("|_______________________________________________________|");
                System.out.println("|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|");
                System.out.println("|_________________________Log In________________________|");
                System.out.print("|\tPlease log in to your account to continue \t|\n");
                System.out.print("| Enter user ID: ");
                userID = sc.nextLine();
                System.out.print("| Enter pin: ");
                userID = sc.nextLine();

                // attempt to get the customer object corresponding to the entered ID and pin combo
                try {
                    authUser = theBank.userLogin(userID, pin);
                    if (authUser != null) {
                        System.out.println("|_______________________________________________________|");
                        System.out.println("|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|");
                        System.out.println("|__________________Log In Success_______________________|");
                        System.out.println("|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|");
                        break;
                    } else {
                        System.out.println("Login failed " + "Incorrect user ID & pin combination. " +
                                    "Please double check your info and try again.");
                        continue;
                    } 
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                }

                
            } while (authUser == null); // continue looping until successful login by user or quit

            return authUser;
        }

        // displays ATM's menu and avaialable options
        public static void customerMenu(Customer user, Scanner sc) throws FileNotFoundException {
            // print a summary of the users accounts
            System.out.println("|_______________________________________________________|");
            user.printAccountsSummary();
        }
}
