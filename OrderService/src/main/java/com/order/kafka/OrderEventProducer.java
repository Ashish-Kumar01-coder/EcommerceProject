package com.order.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Service;

import com.order.dto.OrderEvent;

@Service
public class OrderEventProducer {
	
	
	@Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    private static final String TOPIC = "ECOMMERCEEVENTS";

    public void publishOrder(OrderEvent event) {
        kafkaTemplate.send(TOPIC, String.valueOf(event.getOrderId()), event)
                     .whenComplete((result, ex) -> {
                         if (ex != null) {
                             System.err.println("Failed to publish ORDER_CREATED: " + ex.getMessage());
                         } else {
                             System.out.println("ORDER_CREATED event published for orderId=" + event.getOrderId());
                         }
                     });
    }

}
