package org.tretton63.bank.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AccountView {
    private static final Logger logger = LoggerFactory.getLogger(AccountView.class);
    @Id
    String accountNumber;
    String accountName;
    int balance;

    public AccountView() {
    }

    public AccountView(String accountNumber, String accountName, int balance) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public int getBalance() {
        return balance;
    }


    public String getAccountName() {
        return accountName;
    }

    public void addAmount(int amount) {
        this.balance += amount;
    }

    public void subAmount(int amount) {
        this.balance -= amount;
    }

}
