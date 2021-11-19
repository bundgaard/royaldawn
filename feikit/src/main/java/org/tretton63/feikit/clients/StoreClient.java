package org.tretton63.feikit.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.tretton63.feikit.model.ComplexExample;

import java.util.List;

@FeignClient(
        name = "stores",
        url = "http://localhost:8080",
        decode404 = true
)
public interface StoreClient {
    @GetMapping("/stores")
    List<String> getStores();

    @GetMapping("/stores/1")
    String getSpecificStore();

    @GetMapping("/stores/2")
    ComplexExample getComplexExample();

    @GetMapping("/stores/3")
    ComplexExample getComplexExampleNotFound();

    @GetMapping("/stores/4")
    List<String> talkToCustomersEndpoint();
}
