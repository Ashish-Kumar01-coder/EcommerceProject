package com.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.order.dto.UserOrderRequest;
import com.order.entity.OrderEntity;
import com.order.service.ServiceLayer;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private ServiceLayer serviceLayer;

    @PostMapping
    public ResponseEntity<OrderEntity> createOrder(
            @RequestBody UserOrderRequest request) {

        return ResponseEntity.ok(serviceLayer.createOrder(request));
    }

    @GetMapping("/{id}")
    public OrderEntity getOrder(@PathVariable int id) {
        return serviceLayer.getOrder(id);
    }
}