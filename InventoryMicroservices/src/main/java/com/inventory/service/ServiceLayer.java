package com.inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventory.dao.InventoryRepository;
import com.inventory.entity.Inventory;
import com.inventory.feign.Product;
import com.inventory.feign.ProductClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;

@Service
public class ServiceLayer {
	
	@Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductClient productClient;
    
    @Autowired
    Tracer tracer;
    
  
    public Inventory createInventory(Inventory inventory) {

        // validate product exists
        productClient.getProduct(inventory.getProductId());

        Span span = tracer.nextSpan()
                .name("inventory-create")
                .tag("service", "inventory-service")
                .start();

        span.end();

        return inventoryRepository.save(inventory);
    }

 // ✅ GET INVENTORY
    @CircuitBreaker(name = "product-service", fallbackMethod = "productFallback")
    public Inventory getInventory(int productId) {

        productClient.getProduct(productId);

        return inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
    }

   // ✅ REDUCE STOCK
    public Inventory reduceStock(int productId, int quantity) {

        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

        if (inventory.getStock() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }

        inventory.setStock(inventory.getStock() - quantity);
        return inventoryRepository.save(inventory);
    }
    
  
    public Inventory productFallback(int productId, Throwable ex) {
        throw new RuntimeException("PRODUCT-SERVICE-DOWN");
    }
    
    
    public String checkStock(int productId) {

        Span span = tracer.nextSpan()
                .name("inventory-service")
                .tag("productId", String.valueOf(productId))
                .start();

        span.end();

        return "INVENTORY-SERVICE";
    }

}
