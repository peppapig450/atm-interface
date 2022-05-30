package com.java.atmInterface;

import java.util.Scanner;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


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
                    throw new RuntimeException(e.getMessage());
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e.getMessage());
                }

                
            } while (authUser == null); // continue looping until successful login by user or quit

            return authUser;
        }

        // displays ATM's menu and avaialable options
        public static void customerMenu(Customer user, Scanner sc) {
            int choice;
            // print a summary of the users accounts
            System.out.println("|_______________________________________________________|");
            user.printAccountsSummary();

            // user menu
            do {
                System.out.println("|What would you like to do?");
                System.out.println("|  1) Deposit ");
                System.out.println("|  2) Withdraw");
                System.out.println("|  3) Transfer");
                System.out.println("|  4) Show account transaction history");
                System.out.println("|  5) Overdraft options");
                System.out.println("|  6) Logout of/Quit the Application");
                System.out.println();
                System.out.println("| Enter choice: ");
                choice = sc.nextInt();

                // if the choice isn't within the available options prompt again
                if (choice < 1 || choice > 6) {
                    System.out.println("Invalid choice. Please choose 1-6.");
                }

            } while (choice < 1 || choice > 6); // keep looping until a valid option is entered

            // process user input
            switch (choice) {
                case 1:
                    ATM.depositFunds(user, sc);
                    break;
                case 2:
                    ATM.withdrawFunds(user, sc);
                    break;
                case 3:
                    ATM.transferFunds(user, sc);
                    break;
                case 4:
                    ATM.showTransferHistory(user, sc); // print all transactions for user on screen
                case 5:
                    System.out.println("Please contact your branch to discuss available overdraft options. \n"); // overdraft options
                    ATM.overdraftMenu(user, sc);
                    break;
                case 6:
                    sc.nextLine();
                    break;
            }

            // display menu until user wishes to quite
            if (choice != 6) {
                ATM.customerMenu(user, sc);
            }
        }

        public static void overdraftMenu(Customer user, Scanner sc) {}

        // access the abstract payOverDraft method with static
        public Object Start() {
            payOverDraft();
            return null;
        }

        // transfer funds from one account to another
        public static void transferFunds(Customer user, Scanner sc) {

            int fromAccount, toAccount;
            double amount, accountBal;

            if (user.numAccounts() != 1) {
                // get the account to transfer FROM
                do {
                    System.out.printf("Enter the number (1-%d) of the account to transfer from: ", user.numAccounts());
                    fromAccount = sc.nextInt() - 1;
                    // check if the entered value is an actual account number
                    if (fromAccount < 0 || fromAccount >= user.numAccounts()) {
                        System.out.println("Invalid account. Please try again.");
                    }
                } while (fromAccount < 0 || fromAccount >= user.numAccounts()); // loop until a valid account is entered

            } else {
                fromAccount = 1;
            }

            accountBal = user.getAccountBalance(fromAccount);

            // get the account to transfer TO
            do {
                System.out.printf("Enter the number (1-%d) of the account to transfer to: ", user.numAccounts());
                toAccount = sc.nextInt() - 1;

                // check if the entered value is an actual account number
                if (toAccount < 0 || toAccount >= user.numAccounts()) {
                    System.out.println("Invalid account. Please try again.");
                }
            } while (toAccount < 0 || toAccount >= user.numAccounts()); // loop until a valid account is entered
        

             // get amount of funds to transfer
            do {
                System.out.printf("Enter the amount to transfer (max $%.02f): $", accountBal);
                amount = sc.nextDouble();
                // make sure amount is greater than 0 and not exceeding the account's balance
                if (amount < 0) {
                    System.out.println("Amount must be greater than zero.");
                } else if (amount > accountBal) {
                    System.out.printf("Amount must be not greater than balance of $%.02f.\n", accountBal);
                }
            } while (amount < 0 || amount > accountBal); // loop until valid amount is entered
            
            
            user.addAccountTransaction(fromAccount, amount, String.format("Transfer to account %s",
                user.getAccountUUID(toAccount)));

            user.addAccountTransaction(toAccount, amount, String.format("Transfer from ac "));
        }


                    


}
