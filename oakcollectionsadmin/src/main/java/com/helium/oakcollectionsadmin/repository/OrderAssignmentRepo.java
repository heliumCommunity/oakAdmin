package com.helium.oakcollectionsadmin.repository;

import com.helium.oakcollectionsadmin.entity.OrderAssignmentEntity;
import com.helium.oakcollectionsadmin.entity.OrderTracker;
import com.helium.oakcollectionsadmin.entity.StatusTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderAssignmentRepo extends JpaRepository<OrderAssignmentEntity,Long> {
    @Query("SELECT j FROM OrderTracker  j WHERE LOWER(j.orderId) = LOWER(:orderId)")
    Optional<OrderTracker> findByOrderId(@Param("orderId") String orderId);

}
