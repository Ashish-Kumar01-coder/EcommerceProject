package com.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.entity.Inventory;
import com.inventory.feign.ReduceStockRequest;
import com.inventory.service.ServiceLayer;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private ServiceLayer serviceLayer;
    
   
    @PostMapping
    public Inventory create(@RequestBody Inventory inventory) {
        return serviceLayer.createInventory(inventory);
    }

   
    @GetMapping("/{productId}")
    public Inventory get(@PathVariable int productId) {
        return serviceLayer.getInventory(productId);
    }
    
    @PutMapping("/reduce")
    public Inventory reduce(@RequestBody ReduceStockRequest request) {
        return serviceLayer.reduceStock(
            request.getProductId(),
            request.getQuantity()
        );
    }

   
    @GetMapping("/check/{productId}")
    public String check(@PathVariable int productId) {
        return serviceLayer.checkStock(productId);
    }
}