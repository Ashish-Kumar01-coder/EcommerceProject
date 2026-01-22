package com.paymentservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.paymentservice.dao.PaymentRepository;

import com.paymentservice.dto.OrderEvent;
import com.paymentservice.entity.PaymentEntity;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;

@Service
public class ServiceLayer {
	
	
	 @Autowired
	    private PaymentRepository paymentRepository;

	    @Autowired
	    private KafkaTemplate<String, Object> kafkaTemplate;

	    @Autowired
	    private Tracer tracer;
	    
	    
	    public void processPayment(OrderEvent event) {

	    
	        if (!"ORDER_CREATED".equals(event.getEventType())) {
	            return;
	        }

	        Span span = tracer.nextSpan()
	                .name("payment-service")
	                .tag("orderId", String.valueOf(event.getOrderId()))
	                .start();

	        try {
	            // ---- PAYMENT LOGIC (SIMULATED)
	            boolean success = true;

	            PaymentEntity payment = new PaymentEntity();
	            payment.setOrderId(event.getOrderId());
	            payment.setUserId(event.getUserId());
	            payment.setAmount(event.getAmount());
	            payment.setStatus(success ? "SUCCESS" : "FAILED");

	            paymentRepository.save(payment);

	            // ---- PRODUCE PAYMENT EVENT
	            OrderEvent paymentEvent = new OrderEvent();
	            paymentEvent.setOrderId(event.getOrderId());
	            paymentEvent.setUserId(event.getUserId());
	            paymentEvent.setAmount(event.getAmount());
	            paymentEvent.setEventType(
	                    success ? "PAYMENT_SUCCESS" : "PAYMENT_FAILED"
	            );

	            kafkaTemplate.send("ECOMMERCEEVENTS", paymentEvent);

	        } finally {
	            span.end();
	        }
	    }
	    
	    
	    public int createTable(OrderEvent orderEvent) {
	        PaymentEntity paymentEntity = new PaymentEntity();
	        paymentEntity.setOrderId(orderEvent.getOrderId());
	        paymentEntity.setAmount(orderEvent.getAmount());
	        paymentEntity.setStatus("Success");
	        paymentEntity.setUserId(orderEvent.getUserId());
	        
	        paymentEntity = paymentRepository.save(paymentEntity);
	        
	        return paymentEntity.getId();
	    }


}
