package com.evertix.tutofastbackend.service;

import com.evertix.tutofastbackend.model.Review;
import com.evertix.tutofastbackend.resource.ReviewResource;
import com.evertix.tutofastbackend.resource.ReviewSaveResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ReviewService {
    Page<ReviewResource> getAllReview(Pageable pageable);
    Page<ReviewResource> getReviewsByTeacher(Long teacherId, Pageable pageable);
    ReviewResource createReview(Long studentId,Long teacherId,ReviewSaveResource newReview);
    ReviewResource updateReview(Long reviewId, ReviewSaveResource reviewDetails);
    ResponseEntity<?> deleteReview(Long reviewId);
}
