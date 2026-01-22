package com.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.bean.ProductBean;
import com.product.entity.ProductEntity;
import com.product.service.ServiceLayer;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ServiceLayer serviceLayer;

    @PostMapping
    public ResponseEntity createProduct(@RequestBody ProductBean bean) {
        int id = serviceLayer.craeteData(bean);
        
        return ResponseEntity.ok("Product details recieved to table id : " + id);
    }

    @GetMapping("/{id}")
    public ProductEntity getProduct(@PathVariable int id) {
        return serviceLayer.getProduct(id);
    }
    
    
    
   
    @GetMapping("/check-user/{id}")
    public String checkUser(@PathVariable int id) {
        return serviceLayer.callUser(id);
    }
}
