package com.helium.oakcollectionsadmin.serviceImpls;

import com.helium.oakcollectionsadmin.dto.SignUpRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserIdGenerationService {

    public String IdGeneration(String email) {
        try {
            SecureRandom rand = new SecureRandom();
            int max = 9999;
            int min = 1000;
            int randomNumber = rand.nextInt((max - min) + 1) + min;
            log.info("Generating Number Part of UserId {}", randomNumber);
            String numberPart = String.valueOf(randomNumber);
            String stringPart = email.substring(0,2);
            String userId = numberPart + stringPart;
            log.info("Generated UserId - {}", userId);
            return userId;


        } catch (Exception e) {
            log.error("Error Generating UserId : {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }

    }
}
