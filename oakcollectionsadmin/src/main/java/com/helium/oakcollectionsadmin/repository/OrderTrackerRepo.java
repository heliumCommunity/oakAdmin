package com.helium.oakcollectionsadmin.repository;

import com.helium.oakcollectionsadmin.entity.OrderTracker;
import com.helium.oakcollectionsadmin.enums.status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderTrackerRepo extends JpaRepository<OrderTracker, Long> {
    OrderTracker findByStatus(String status);
    OrderTracker findByCustomerName(String CustomerName);

}
