package org.tretton63.feikit.interfaces.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tretton63.feikit.filters.RequestIdFilter;

import java.util.List;

@Slf4j
@RestController
public class CustomerController {

    @GetMapping("/customers")
    public List<String> allCustomers(RequestIdFilter.RequestId requestId) {
        log.info("allCustomers requestId={}", requestId);
        return List.of("Customer1", "Customer2", "Customer3");
    }
}
