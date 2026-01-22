package com.notificationservice.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.notificationservice.dto.OrderEvent;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;

@Service
public class NotificationConsumer {
	
	private final Tracer tracer;

    public NotificationConsumer(Tracer tracer) {
        this.tracer = tracer;
    }

    
    @KafkaListener(topics = "ECOMMERCEEVENTS", groupId = "notification-group")
    public void consume(OrderEvent event) {

        if (event == null || event.getEventType() == null) {
            System.out.println("[NotificationConsumer] ⚠ Received invalid or incomplete event, skipping...");
            return;
        }

        switch (event.getEventType()) {
            case "ORDER_CREATED":
                System.out.println("[NotificationConsumer] ✅ Order has been created successfully! OrderId: " + event.getOrderId());
                break;

            case "PAYMENT_SUCCESS":
                System.out.println("[NotificationConsumer] 💰 Payment completed successfully for OrderId: " + event.getOrderId());
                break;

            default:
                System.out.println("[NotificationConsumer] ⚠ Unknown event type: " + event.getEventType());
        }
    }

}
