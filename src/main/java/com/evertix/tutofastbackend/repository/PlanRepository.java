package com.evertix.tutofastbackend.repository;

import com.evertix.tutofastbackend.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends JpaRepository<Plan,Long> {

}
