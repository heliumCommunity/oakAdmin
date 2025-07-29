package com.helium.oakcollectionsadmin.repository;


import com.helium.oakcollectionsadmin.entity.CustomerModule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<CustomerModule,Long> {
    Optional<CustomerModule> findByCustomerPhoneNumber(String phoneNumber);
}
