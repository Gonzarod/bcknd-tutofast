package com.evertix.tutofastbackend.service;

import com.evertix.tutofastbackend.model.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface SubscriptionService {
    Page<Subscription> getAllSubscriptions(Pageable pageable);
    Page<Subscription> getUsersSubscriptions(Long userId, Pageable pageable);
    ResponseEntity<?> subscribeToPlan(Long userId,Long planId); // User (Teacher, Student)
    ResponseEntity<?> unsubscribeToPlan(Long userId, Long planId); // User (Teacher, Student)
    ResponseEntity<?> deleteSubscription(Long userId, Long planId,Long subscriptionId);
}
