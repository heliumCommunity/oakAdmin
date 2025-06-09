package com.helium.oakcollectionsadmin.repository;

import com.helium.oakcollectionsadmin.entity.OrderTracker;
import com.helium.oakcollectionsadmin.entity.StatusTracker;
import org.hibernate.query.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StatusTrackerRepo extends JpaRepository<StatusTracker, Long> {
    @Query("SELECT j FROM StatusTracker  j WHERE LOWER(j.orderId) = LOWER(:orderId)")
    Optional<StatusTracker> findByOrderId(@Param("orderId") String orderId);
}
