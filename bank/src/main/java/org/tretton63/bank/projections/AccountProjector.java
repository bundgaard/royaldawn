package org.tretton63.bank.projections;

import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.tretton63.bank.coreapi.event.AccountCreatedEvent;
import org.tretton63.bank.coreapi.event.DepositedEvent;
import org.tretton63.bank.coreapi.event.WithdrawedEvent;
import org.tretton63.bank.coreapi.query.FindAccountQuery;
import org.tretton63.bank.dto.AccountDto;
import org.tretton63.bank.entity.AccountView;
import org.tretton63.bank.repository.AccountViewRepository;

@Component
public class AccountProjector {

    private static final Logger logger = LoggerFactory.getLogger(AccountProjector.class);
    private AccountViewRepository accountViewRepository;

    public AccountProjector(AccountViewRepository accountViewRepository) {
        this.accountViewRepository = accountViewRepository;
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        logger.debug("received AccountCreatedEvent {}", event);
        AccountView view = new AccountView(event.accountNumber(), event.accountName(), event.balance());
        logger.debug("AccountCreatedEvent view {}", view.getAccountNumber());
        accountViewRepository.save(view);
    }

    @EventSourcingHandler
    public void on(DepositedEvent event) {
        accountViewRepository.findById(event.accountNumber()).ifPresent(view -> view.addAmount(event.amount()));
    }

    @EventSourcingHandler
    public void on(WithdrawedEvent event) {
        accountViewRepository.findById(event.accountNumber()).ifPresent(view -> view.subAmount(event.amount()));
    }

    @QueryHandler
    public AccountDto handle(FindAccountQuery query) {
        return accountViewRepository
                .findById(query.accountNumber())
                .map(view -> new AccountDto(
                        view.getAccountName(),
                        view.getAccountNumber(),
                        view.getBalance())
                ).orElse(null);
    }

}
