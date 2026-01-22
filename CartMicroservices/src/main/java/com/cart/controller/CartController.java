package com.cart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cart.entity.CartItem;
import com.cart.service.ServiceLayer;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ServiceLayer serviceLayer;
    
    
    @PostMapping("/add")
    public String addToCart(@RequestParam int userId,
                            @RequestParam int productId,
                            @RequestParam int quantity) {

        return serviceLayer.addToCart(userId, productId, quantity);
    }

    @DeleteMapping("/remove")
    public String removeFromCart(@RequestParam int userId,
                                 @RequestParam int productId) {

        return serviceLayer.removeFromCart(userId, productId);
    }
    
    
    @GetMapping("/{userId}")
    public List<CartItem> getCart(@PathVariable int userId) {
        return serviceLayer.getCartItems(userId);
    }

   
}