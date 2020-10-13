package com.evertix.tutofastbackend.service;

import com.evertix.tutofastbackend.model.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface SubscriptionService {
    Page<Subscription> getAllSubscriptions(Pageable pageable);
    Page<Subscription> getUsersSubscriptions(Long userId, Pageable pageable);
    Subscription subscribeToPlan(Long userId,Long planId); // User (Teacher, Student)
    Subscription unsubscribeToPlan(Long userId, Long planId, Long subscriptionId); // User (Teacher, Student)
    ResponseEntity<?> deleteSubscription(Long userId, Long planId,Long subscriptionId);
}
