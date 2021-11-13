package org.tretton63.bank.coreapi.event;

public record AccountCreatedEvent(
        String accountNumber,
        String accountName,
        int balance
) {

}
