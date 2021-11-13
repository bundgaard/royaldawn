package org.tretton63.bank;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.tretton63.bank.coreapi.command.DepositCommand;
import org.tretton63.bank.coreapi.command.WithdrawCommand;
import org.tretton63.bank.coreapi.query.FindAccountQuery;
import org.tretton63.bank.dto.AccountDto;

import java.util.Random;

@Component
public class BankExample {

    private CommandGateway commandGateway;
    private QueryGateway queryGateway;
    private Random random;
    private EventGateway eventGateway;
    private EventStore eventStore;

    public BankExample(CommandGateway commandGateway, QueryGateway queryGateway, EventGateway eventGateway, EventStore eventStore) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
        this.eventGateway = eventGateway;
        this.eventStore = eventStore;
        this.random = new Random();
    }

    private String createAccountNumber(int length) {
        String digits = "0123456789";
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < length; i++) {
            out.append(digits.charAt(random.nextInt(digits.length())));
        }

        return out.toString();
    }

    private static final Logger logger = LoggerFactory.getLogger(BankExample.class);

    @Bean
    public CommandLineRunner bankExampleRunner() {
        return args -> {
            String accountNumber = "439524982618";
            String accountName = "Microsoft";

            // commandGateway.sendAndWait(new CreateAccountCommand(accountNumber, accountName, 0));
            commandGateway.sendAndWait(new DepositCommand(accountNumber, 100000));
            commandGateway.sendAndWait(new WithdrawCommand(accountNumber, 1000));

            AccountDto view = queryGateway.query(new FindAccountQuery(accountNumber), ResponseTypes.instanceOf(AccountDto.class)).get();
            if (view == null) {
                logger.info("no view for account {}", accountName);
            } else {
                logger.info("Account {}", view);
            }

            try {
                commandGateway.send(new WithdrawCommand(accountNumber, Integer.MAX_VALUE));
            } catch (Exception e) {
                logger.info(e.getMessage(), e);
            }

        };
    }
}
