package org.tretton63.bank.coreapi.event;

public record WithdrawedEvent(
        String accountNumber,
        int amount
) {

}
