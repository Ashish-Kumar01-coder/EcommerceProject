package com.cart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cart.dao.CartItemRepository;
import com.cart.dao.CartRepository;
import com.cart.entity.Cart;
import com.cart.entity.CartItem;
import com.cart.feign.ProductClient;
import com.cart.feign.UserClient;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;

@Service
@Transactional
public class ServiceLayer {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserClient userClient;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private Tracer tracer;

    public String addToCart(int userId, int productId, int quantity) {

        userClient.getUser(userId);
        productClient.getProduct(productId);

        Span span = tracer.nextSpan().name("add-to-cart").start();
        try (Tracer.SpanInScope ws = tracer.withSpan(span)) {

            Cart cart = cartRepository.findByUserId(userId)
                    .orElseGet(() -> {
                        Cart c = new Cart();
                        c.setUserId(userId);
                        return cartRepository.save(c);
                    });

            CartItem item = cartItemRepository
                    .findByCartIdAndProductId(cart.getId(), productId)
                    .orElse(new CartItem());

            item.setProductId(productId);
            item.setQuantity(item.getQuantity() + quantity);
            item.setCart(cart);

            cartItemRepository.save(item);

            return "ITEM ADDED TO CART";
        } finally {
            span.end();
        }
    }

    public String removeFromCart(int userId, int productId) {

        userClient.getUser(userId);

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        CartItem item = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        Span span = tracer.nextSpan().name("remove-from-cart").start();
        try (Tracer.SpanInScope ws = tracer.withSpan(span)) {

            cartItemRepository.delete(item);   // ✅ ACTUAL DELETE

            return "ITEM REMOVED FROM CART";
        } finally {
            span.end();
        }
    }

    public List<CartItem> getCartItems(int userId) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        return cartItemRepository.findByCart_Id(cart.getId());
    }

    /**
     * Clear entire cart after order placed
     */
    public void clearCartByUserId(int userId) {
        cartItemRepository.deleteByCart_UserId(userId);
    }
}
