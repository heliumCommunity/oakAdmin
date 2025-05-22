package com.helium.oakcollectionsadmin.repository;

import com.helium.oakcollectionsadmin.dto.GetOrderByCustomerNameRequest;
import com.helium.oakcollectionsadmin.dto.GetOrderRequest;
import com.helium.oakcollectionsadmin.entity.OrderTracker;
import com.helium.oakcollectionsadmin.enums.status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface OrderTrackerRepo extends JpaRepository<OrderTracker, Long> {
    OrderTracker findByStatus(status status);
    OrderTracker findByCustomerName(@Param("customerName")String CustomerName);

}
