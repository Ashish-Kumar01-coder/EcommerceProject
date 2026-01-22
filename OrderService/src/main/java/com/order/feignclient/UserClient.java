package com.order.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@FeignClient(name = "user-service", url = "http://localhost:8080")
public interface UserClient {
	@GetMapping("/users/{id}")
    String getUser(@PathVariable int id);
}
