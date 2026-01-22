package com.review.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.review.entity.Review;
import com.review.service.ReviewService;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public String addReview(@RequestBody Review review) {
        return reviewService.addReview(review);
    }

    @GetMapping("/product/{id}")
    public Map<String, Object> getReviews(@PathVariable int id) {
        return reviewService.getReviewsByProduct(id);
    }
}