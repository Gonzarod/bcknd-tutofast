package com.evertix.tutofastbackend.repository;

import com.evertix.tutofastbackend.model.Plan;
import com.evertix.tutofastbackend.model.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription,Long> {
    Page<Subscription> findAllByUserId(Long userId, Pageable pageable);
   // Subscription findByUserIdAndActiveIsTrue(Long userId);
}
