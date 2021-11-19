package org.tretton63.feikit.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(
        name = "customers",
        url = "http://localhost:8080",
        decode404 = true
)
public interface CustomerClient {

    @GetMapping("/customers")
    List<String> getCustomers(@RequestHeader String requestId);
}
