package org.tretton63.bank.coreapi.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record WithdrawCommand(
        @TargetAggregateIdentifier String accountNumber,
        int amount
) {
}
