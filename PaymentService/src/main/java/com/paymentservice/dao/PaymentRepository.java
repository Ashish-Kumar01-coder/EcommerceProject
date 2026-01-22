package com.paymentservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;


import com.paymentservice.entity.PaymentEntity;



public interface PaymentRepository extends JpaRepository<PaymentEntity, Integer> {
}
