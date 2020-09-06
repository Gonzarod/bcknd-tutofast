package com.evertix.tutofastbackend.service;

import com.evertix.tutofastbackend.exception.ResourceNotFoundException;
import com.evertix.tutofastbackend.model.Review;
import com.evertix.tutofastbackend.repository.ReviewRepository;
import com.evertix.tutofastbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserRepository userRepository;



    @Override
    public Page<Review> getAllReview(Pageable pageable) {
        return reviewRepository.findAll(pageable);
    }

    @Override
    public Page<Review> getReviewsByTeacher(Long teacherId, Pageable pageable) {
        return reviewRepository.getAllByTeacherId(teacherId,pageable);
    }

    @Override
    public Review createReview(Long studentId,Long teacherId,Review review) {
        return userRepository.findById(studentId).map(student ->{
            return userRepository.findById(teacherId).map(teacher ->{
                review.setStudent(student);
                review.setTeacher(teacher);
                return reviewRepository.save(review);
            }).orElseThrow(()-> new ResourceNotFoundException("Teacher with id: "+teacherId+"not found"));
        }).orElseThrow(()-> new ResourceNotFoundException("Student with id: "+studentId+"not found"));
    }

    @Override
    public Review updateReview(Long reviewId, Review reviewDetails) {
        return reviewRepository.findById(reviewId).map(review ->{
            review.setDescription(reviewDetails.getDescription());
            review.setStars(reviewDetails.getStars());
            return reviewRepository.save(review);
        }).orElseThrow(()-> new ResourceNotFoundException("Review with id: "+reviewId+"not found"));
    }

    @Override
    public ResponseEntity<?> deleteReview(Long reviewId) {
        return reviewRepository.findById(reviewId).map(review ->{
            reviewRepository.delete(review);
            return ResponseEntity.ok().build();
        }).orElseThrow(()-> new ResourceNotFoundException("Review with id: "+reviewId+"not found"));
    }

}
