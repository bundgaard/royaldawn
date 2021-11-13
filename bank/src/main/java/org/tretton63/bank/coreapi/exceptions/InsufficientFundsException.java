package org.tretton63.bank.coreapi.exceptions;

public class InsufficientFundsException extends Exception {
    public InsufficientFundsException() {
        super("Insufficient funds on account");
    }
}
