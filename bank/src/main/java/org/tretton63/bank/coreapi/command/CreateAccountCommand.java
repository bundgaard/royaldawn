package org.tretton63.bank.coreapi.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record CreateAccountCommand(@TargetAggregateIdentifier String accountNumber, String accountName, int balance) {
}
