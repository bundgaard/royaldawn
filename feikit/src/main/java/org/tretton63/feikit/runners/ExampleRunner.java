package org.tretton63.feikit.runners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.tretton63.feikit.clients.StoreClient;
import org.tretton63.feikit.model.ComplexExample;

import java.util.List;

@Component
public class ExampleRunner {

    public ExampleRunner(StoreClient storeClient) {
        this.storeClient = storeClient;
    }

    private static final Logger logger = LoggerFactory.getLogger(ExampleRunner.class);
    private final StoreClient storeClient;

    @Bean
    public CommandLineRunner exampleCommandRunner() {
        return args -> {
            List<String> stores = storeClient.getStores();
            logger.info("requesting stores {}",  stores);
            String specificStore = storeClient.getSpecificStore();
            logger.info("Not found excpetion {}", specificStore);
            ComplexExample complexExample = storeClient.getComplexExample();
            logger.info("Complex exampled {}", complexExample);
            logger.info("Complex example not found {}", storeClient.getComplexExampleNotFound());
        };
    }
}
