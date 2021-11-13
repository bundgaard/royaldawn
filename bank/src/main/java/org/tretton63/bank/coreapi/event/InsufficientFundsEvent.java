package org.tretton63.bank.coreapi.event;

public record InsufficientFundsEvent(String accountNumber, int amount) {
}
