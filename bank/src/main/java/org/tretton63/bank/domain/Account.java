package org.tretton63.bank.domain;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tretton63.bank.coreapi.command.CreateAccountCommand;
import org.tretton63.bank.coreapi.command.DepositCommand;
import org.tretton63.bank.coreapi.command.WithdrawCommand;
import org.tretton63.bank.coreapi.event.AccountCreatedEvent;
import org.tretton63.bank.coreapi.event.DepositedEvent;
import org.tretton63.bank.coreapi.event.InsufficientFundsEvent;
import org.tretton63.bank.coreapi.event.WithdrawedEvent;
import org.tretton63.bank.coreapi.exceptions.InsufficientFundsException;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class Account {

    private static final Logger logger = LoggerFactory.getLogger(Account.class);

    @AggregateIdentifier
    private String accountNumber;
    private int balance;
    private String accountName;

    public Account() {
    }

    @CommandHandler
    public Account(CreateAccountCommand command) {
        logger.info("received command {}", command);
        apply(new AccountCreatedEvent(
                command.accountNumber(),
                command.accountName(),
                command.balance()));
    }


    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        logger.info("received event {}", event);
        this.accountNumber = event.accountNumber();
        this.accountName = event.accountName();
        this.balance = event.balance();
    }

    @CommandHandler
    public void handle(DepositCommand command) {
        logger.info("received command {}", command);
        apply(new DepositedEvent(command.accountName(), command.amount()));

    }

    @EventSourcingHandler
    public void on(DepositedEvent event) {
        logger.info("received deposited event {}", event);
        this.balance += event.amount();
    }

    @CommandHandler
    public void handle(WithdrawCommand command) throws InsufficientFundsException {
        if (command.amount() > this.balance) {
            apply(new InsufficientFundsEvent(command.accountNumber(), command.amount()));
            throw new InsufficientFundsException();
        }
        apply(new WithdrawedEvent(command.accountNumber(), command.amount()));
    }

    @EventSourcingHandler
    public void on(WithdrawedEvent command) {
        this.balance -= command.amount();
    }
}


