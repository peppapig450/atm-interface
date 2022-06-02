package main.java.com.apcsa;

public class CreditAccount extends Account {

    private double overDraftLimit;

    // constructor for a credit card account object
    public CreditAccount(String name, Customer holder, Bank bank, double overDraftLimit) {
        super(name, holder, bank);

        this.overDraftLimit = overDraftLimit;

    }

    public double getBalance() {
        double balance = 0;
        balance = overDraftLimit;
        return balance;
    }

}
