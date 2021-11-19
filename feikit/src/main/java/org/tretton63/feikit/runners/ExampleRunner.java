package org.tretton63.feikit.runners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.tretton63.feikit.clients.StoreClient;
import org.tretton63.feikit.model.ComplexExample;

import java.util.List;

@Slf4j
@Component
public class ExampleRunner {

    private final StoreClient storeClient;

    public ExampleRunner(StoreClient storeClient) {
        this.storeClient = storeClient;
    }


    @Bean
    public CommandLineRunner exampleCommandRunner() {
        return args -> {
            List<String> stores = storeClient.getStores();
            log.info("requesting stores {}", stores);
            String specificStore = storeClient.getSpecificStore();
            log.info("Not found exception {}", specificStore);
            ComplexExample complexExample = storeClient.getComplexExample();
            log.info("Complex exampled {}", complexExample);
            log.info("Complex example not found {}", storeClient.getComplexExampleNotFound());
            log.info("Store 4 example with talking to Customers endpoint {}", storeClient.talkToCustomersEndpoint());

        };
    }
}
