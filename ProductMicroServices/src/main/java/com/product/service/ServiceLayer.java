package com.product.service;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.Span;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.product.bean.ProductBean;
import com.product.entity.CategoryEntity;
import com.product.entity.ProductEntity;
import com.product.feignclient.UserBean;
import com.product.feignclient.UserClient;
import com.product.jpa.CategoryRepository;
import com.product.jpa.ProductRepository;

@Service
public class ServiceLayer {
	
	
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	UserClient userClient;
	
	@Autowired
	Tracer tracer;
	
	public int craeteData(ProductBean productBean) {
		String name = productBean.getDescription();
		 CategoryEntity category = categoryRepository
	                .findByName(name)
	                .orElseGet(() -> {
	                    CategoryEntity newCategory = new CategoryEntity();
	                    newCategory.setName(name);
	                    return categoryRepository.save(newCategory);
	                });
		 
		 
		 ProductEntity productEntity = new ProductEntity();
		 productEntity.setName(productBean.getName());
		 productEntity.setDescription(productBean.getDescription());
		 productEntity.setPrice(productBean.getPrice());
		 productEntity.setCategory(category);
		 
		 productEntity = productRepository.save(productEntity);
		 
		 return productEntity.getId();
		 
		 
		 
		 
				
	}
	
	
	public ProductEntity getProduct(int id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
	
	
	 public String callUser(int id) {
	        return userClient.userDetails(id);
	    }
	 
	 public String validateUser(int userId) {

	        Span span = tracer.nextSpan(tracer.currentSpan())
	                .name("product-service")
	                .tag("userId", String.valueOf(userId))
	                .start();

	       

	        span.end();

	        return "PRODUCT-SERVICE";
	    }
	

}
