package com.paymentservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paymentservice.dao.PaymentRepository;
import com.paymentservice.dto.OrderEvent;
import com.paymentservice.entity.PaymentEntity;
import com.paymentservice.service.ServiceLayer;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    ServiceLayer serviceLayer;

    @PostMapping
    public int create(@RequestBody OrderEvent orderEvent) {
        int id = serviceLayer.createTable(orderEvent);
        
        return id;
    }
}