package com.order.feignclient;



import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.order.dto.CartItemDTO;

@FeignClient(name = "cart-service", url = "http://localhost:8083")
public interface CartClient {

	@GetMapping("/cart/{userId}")
    List<CartItemDTO> getCart(@PathVariable int userId);

    @DeleteMapping("/cart/remove")
    void removeFromCart(@RequestParam int userId,
                        @RequestParam int productId);
}
