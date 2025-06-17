package com.helium.oakcollectionsadmin.repository;

import com.helium.oakcollectionsadmin.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepo extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findByEmail(String email);
    UserInfo findByStaffId(String staffId);

}
