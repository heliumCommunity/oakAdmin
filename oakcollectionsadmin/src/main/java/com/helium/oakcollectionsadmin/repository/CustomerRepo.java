package com.helium.oakcollectionsadmin.repository;


import com.helium.oakcollectionsadmin.entity.CustomerModule;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<CustomerModule,Long> {

    Optional<CustomerModule> findByCustomerPhoneNumber(String phoneNumber);

    @Modifying
    @Transactional
    @Query("UPDATE CustomerModule c SET c.orderCount = c.orderCount + 1 WHERE c.customerPhoneNumber = :customerPhoneNumber")
    CustomerModule orderCount(@Param("customerPhoneNumber") String customerPhoneNumber);
}
