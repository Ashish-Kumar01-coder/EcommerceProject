package com.order.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.dao.OrderRepository;
import com.order.dto.CartItemDTO;
import com.order.dto.OrderEvent;
import com.order.dto.UserOrderRequest;
import com.order.entity.OrderEntity;
import com.order.entity.OrderItemEntity;
import com.order.feignclient.CartClient;
import com.order.feignclient.UserClient;
import com.order.kafka.OrderEventProducer;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;

@Service
public class ServiceLayer {

	@Autowired
    private UserClient userClient;

    @Autowired
    private CartClient cartClient;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderEventProducer producer;

    @Autowired
    private Tracer tracer;
    
    
    public OrderEntity createOrder(UserOrderRequest request) {
    	
    	
    	//from userservices==++---+++==
    	userClient.getUser(request.getUserId());
    	
    	//cartitemdto
    	List<CartItemDTO> cartItemDTOs = cartClient.getCart(request.getUserId());
    	if(cartItemDTOs == null) {
    		throw new RuntimeException("CartItem is not avavailable.");
    	}else {
    		Span span = tracer.nextSpan().name("Order_service").tag("UserId : ", String.valueOf(request.getUserId())).start();
    		span.end();
    	}
    	
    	//save order 
    	
    	OrderEntity orderEntity = new OrderEntity();
    	orderEntity.setUserId(request.getUserId());
    	
    	orderEntity.setStatus("CREATED");
    	
    	List<OrderItemEntity> list = new ArrayList<OrderItemEntity>();
    	double total =0;
    	
    	for(CartItemDTO c : cartItemDTOs) {
    		OrderItemEntity orderItemEntity = new OrderItemEntity();
    		orderItemEntity.setProductId(c.getProductId());
    		orderItemEntity.setPrice(100);
    		orderItemEntity.setQuantity(c.getQuantity());
    		orderItemEntity.setOrder(orderEntity);
    		list.add(orderItemEntity);
    		 total += orderItemEntity.getPrice() * orderItemEntity.getQuantity();
    	}
    	
    	orderEntity.setItems(list);
    	orderEntity.setTotalAmount(total);
    	
    	
    	OrderEntity saveEntity = orderRepository.save(orderEntity);
    	
    	OrderEvent eventProducer = new OrderEvent();
    	eventProducer.setUserId(saveEntity.getUserId());
    	eventProducer.setItems(cartItemDTOs);
    	eventProducer.setOrderId(saveEntity.getId());
    	eventProducer.setEventType("ORDER_CREATED");
    	producer.publishOrder(eventProducer);
    	
    	
    	return saveEntity;
    	
    	
    }
    
    
    public OrderEntity getOrder(int id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

}
