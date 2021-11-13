package org.tretton63.bank.coreapi.event;

public record DepositedEvent(
        String accountNumber,
        int amount
) {
}
