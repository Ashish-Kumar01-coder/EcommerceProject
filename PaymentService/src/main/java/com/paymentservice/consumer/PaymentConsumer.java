package com.paymentservice.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.paymentservice.dto.OrderEvent;
import com.paymentservice.service.ServiceLayer;

@Service
public class PaymentConsumer {

    private static final Logger log = LoggerFactory.getLogger(PaymentConsumer.class);

    @Autowired
    private ServiceLayer paymentService;

    @KafkaListener(
        topics = "ECOMMERCEEVENTS",
        groupId = "payment-service-group"
    )
    public void consume(OrderEvent event) {

        log.info("Received event: {}", event.getEventType());

        // Guard to avoid infinite loops
        if ("ORDER_CREATED".equals(event.getEventType())) {
            paymentService.processPayment(event);
        }
    }
}
