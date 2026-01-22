package com.user.service;


import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.Span;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.bean.UserBean;
import com.user.dao.RoleRepository;
import com.user.dao.UserRepository;
import com.user.entity.CustomerEntity;
import com.user.entity.RoleEntity;

@Service
public class ServiceLayer {
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	Tracer tracer;
	
	public int createUser(UserBean userBean) {

        // Fetch  role from DB================================================
		RoleEntity defaultRole = roleRepository.findByRoleName("CUSTOMER")
		        .orElseThrow(() -> new RuntimeException("Role not found"));

        
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setName(userBean.getName());
        customerEntity.setEmail(userBean.getEmail());
        customerEntity.setPassword(userBean.getPassword());
        customerEntity.setRole(defaultRole);
        
        customerEntity = userRepository.save(customerEntity);
        
        return customerEntity.getId();

        
        
    }
	
	
	public UserBean getUser(int id) {

	    CustomerEntity entity = userRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    UserBean bean = new UserBean();
	    bean.setName(entity.getName());
	    bean.setEmail(entity.getEmail());
	    bean.setPassword(entity.getPassword());;
	   

	    return bean;
	}
	
	public Optional<UserBean> getUserById(int id) {
        return userRepository.findById(id)
                .map(userEntity -> {
                    UserBean user = new UserBean();
                    user.setName(userEntity.getName());
                    user.setEmail(userEntity.getEmail());
                    return user;
                });
    }
	
	 public String userDetails() {
	        return "User service is working";
	    }
	 public String getUser1(int id) {

		 Span span = tracer.nextSpan(tracer.currentSpan())
	                .name("user-service")
	                .tag("userId", String.valueOf(id))
	                .start();

	        span.end();

	        return "USER-SERVICE";

	    }


}
