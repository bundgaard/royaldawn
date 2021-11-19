package org.tretton63.feikit.interfaces.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tretton63.feikit.clients.CustomerClient;
import org.tretton63.feikit.filters.RequestIdFilter;
import org.tretton63.feikit.interfaces.rest.exceptions.NotFoundException;
import org.tretton63.feikit.model.ComplexExample;

import java.util.List;

@Slf4j
@RestController
public class StoreController {

    private final CustomerClient customerClient;

    public StoreController(CustomerClient customerClient) {
        this.customerClient = customerClient;
    }

    @GetMapping("/stores")
    List<String> getStores() {
        log.info("getStores");
        return List.of("hej", "med", "dig");
    }


    @GetMapping("/stores/1")
    List<String> getNotExistingStore() throws NotFoundException {
        throw new NotFoundException(1);
    }


    @GetMapping("/stores/2")
    ComplexExample getComplexExample() {
        ComplexExample obj = new ComplexExample();
        obj.setName("Joakim");
        obj.setObjects(List.of("hej", "med", "dig"));
        obj.setAddress(ComplexExample.Address.builder()
                .city("Ankeborg")
                .postalCode("11100")
                .street("Golden Street 3")
                .build()
        );
        return obj;
    }

    @GetMapping("/stores/3")
    ComplexExample getExampleNotFound() {
        throw new NotFoundException(3);
    }

    @GetMapping("/stores/4")
    List<String> talkToCustomersEndpoint(
            RequestIdFilter.RequestId requestId
    ) {
        log.info("talkToCustomersEndpoint RequestId {}", requestId);
        return customerClient.getCustomers(requestId.getRequestId());
    }
}
