package com.cart.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cart.entity.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    Optional<CartItem> findByCartIdAndProductId(int cartId, int productId);
    
    List<CartItem> findByCart_Id(int cartId);

    void deleteByUserId(int userId);
         void deleteByCart_UserId(int userId);
}
