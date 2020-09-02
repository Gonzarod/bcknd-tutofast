package com.evertix.tutofastbackend.repository;

import com.evertix.tutofastbackend.model.SessionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionDetailsRepository extends JpaRepository<SessionDetails, Long> {
}
