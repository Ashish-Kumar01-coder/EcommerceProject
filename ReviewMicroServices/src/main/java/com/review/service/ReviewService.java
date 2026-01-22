package com.review.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.review.dao.ReviewRepository;
import com.review.entity.Review;
import com.review.feign.ProductClient;
import com.review.feign.UserClient;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;

@Service
public class ReviewService {
	
	@Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserClient userClient;

    @Autowired
    private ProductClient productClient;
    
    @Autowired
    Tracer tracer;

    public String addReview(Review review) {

        Span span = tracer.nextSpan()
                .name("review-service-add-review")
                .tag("userId", String.valueOf(review.getUserId()))
                .tag("productId", String.valueOf(review.getProductId()))
                .start();

        try {
            // Validate user====================/////////////////////
            userClient.validateUser(review.getUserId());

            // Validate product/////////////////////////////////================
            productClient.validateProduct(review.getProductId());

            if (review.getRating() < 1 || review.getRating() > 5) {
                throw new RuntimeException("Rating must be between 1 and 5");
            }

            reviewRepository.save(review);

            return "REVIEW ADDED";
        } finally {
            span.end();
        }
    }

    public Map<String, Object> getReviewsByProduct(int productId) {

        Span span = tracer.nextSpan()
                .name("review-service-get-reviews")
                .tag("productId", String.valueOf(productId))
                .start();

        try {
            List<Review> reviews = reviewRepository.findByProductId(productId);

            double avgRating = reviews.stream()
                    .mapToInt(Review::getRating)
                    .average()
                    .orElse(0.0);

            Map<String, Object> response = new HashMap<>();
            response.put("productId", productId);
            response.put("averageRating", avgRating);
            response.put("reviews", reviews);

            return response;
        } finally {
            span.end();
        }
    }

}
