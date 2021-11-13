package org.tretton63.bank.coreapi.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record DepositCommand(@TargetAggregateIdentifier String accountName, int amount) {
}
