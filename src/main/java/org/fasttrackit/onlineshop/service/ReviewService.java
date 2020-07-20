package org.fasttrackit.onlineshop.service;

import org.fasttrackit.onlineshop.domain.Review;
import org.fasttrackit.onlineshop.persistance.ReviewRepository;
import org.fasttrackit.onlineshop.transfer.review.ReviewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Page<ReviewResponse> getReviews(long productId, Pageable pageable) {
        Page<Review> page = reviewRepository.findByProductId(productId, pageable);

        List<ReviewResponse> reviewDto = new ArrayList<>();

        for (Review review : page.getContent()) {

            ReviewResponse reviewResponse = mapReviewResponse(review);

            reviewDto.add(reviewResponse);
        }
        return new PageImpl<>(reviewDto,pageable,page.getTotalElements());
    }

    private ReviewResponse mapReviewResponse(Review review) {
        ReviewResponse reviewResponse = new ReviewResponse();
        reviewResponse.setId(review.getId());
        reviewResponse.setContent(review.getContent());

        return reviewResponse;
    }
}
