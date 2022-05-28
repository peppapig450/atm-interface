package com.java.atmInterface;

// interface for the methods to handle overdrafts, or when you borrow money
// through your current bank account by taking out more money than you
// have in the account
public interface Overdraft {


    public void payOverDraft(); // method to pay off an overdraft
    public void checkOverDraftRate(); // method to check the overdraft rate for each instance an overdraft occurs
}
