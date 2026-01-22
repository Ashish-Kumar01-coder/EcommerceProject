package com.user.controller;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.bean.UserBean;
import com.user.dao.UserRepository;
import com.user.service.ServiceLayer;

@RestController
@RequestMapping("/users")

public class UserController {
      
	
	@Autowired
	UserRepository userRepository;
	 @Autowired
     ServiceLayer serviceLayer;

    
	@PostMapping
    public ResponseEntity createUser(@RequestBody UserBean userBean) {
    	
    	int id = serviceLayer.createUser(userBean);
    	
    	
    	
        return ResponseEntity.ok("Data is inserted id : " + id);
    }

	@GetMapping("/getuser/{id}")
	public ResponseEntity<UserBean> getUser(@PathVariable int id) {

	    UserBean user = serviceLayer.getUser(id); // use service layer

	    return ResponseEntity.ok(user);
	}
    
    
    @GetMapping("/details")
    public ResponseEntity<String> userDetails() {
        return ResponseEntity.ok(serviceLayer.userDetails());
    }
    
    @GetMapping("/{id}")
    public String getSpan(@PathVariable int id) {
        return serviceLayer.getUser1(id);
    }
    @GetMapping("getusers/{id}")
	public ResponseEntity<UserBean> getUser1(@PathVariable int id) {

        UserBean user = serviceLayer.getUser(id); // use service layer

	    return ResponseEntity.ok(user);
	}
    
}
