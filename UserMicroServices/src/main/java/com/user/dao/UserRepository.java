package com.user.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.user.entity.CustomerEntity;

public interface UserRepository extends JpaRepository<CustomerEntity, Integer>{

	Optional<CustomerEntity> findById(Integer id);


}
