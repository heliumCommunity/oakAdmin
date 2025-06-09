package com.helium.oakcollectionsadmin.serviceImpls;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@Slf4j
@RequiredArgsConstructor
public class IdGenerationService {

    public String UserIdGeneration(String email) {
        try {
            SecureRandom rand = new SecureRandom();
            int max = 9999;
            int min = 1000;
            int randomNumber = rand.nextInt((max - min) + 1) + min;
            log.info("Generating Number Part of UserId {}", randomNumber);
            String numberPart = String.valueOf(randomNumber);
            String stringPart = email.substring(0,2);
            String userId = stringPart.toUpperCase() + numberPart;
            log.info("Generated UserId - {}", userId);
            return userId;


        } catch (Exception e) {
            log.error("Error Generating UserId : {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }

    }
    public String trackingIdGeneration(){
        try {
            SecureRandom rand = new SecureRandom();
            int max = 9999;
            int min = 1000;
            int randomNumber = rand.nextInt((max - min) + 1) + min;
            log.info("Generating  trackingId {}", randomNumber);
            String numberPart = String.valueOf(randomNumber);
            log.info("Generated trackingId - {}", numberPart);
            return numberPart;

    }
        catch (Exception e) {
            log.error("Error Generating trackingId : {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public String orderIdGeneration(){
        try {
            SecureRandom rand = new SecureRandom();
            int max = 9999;
            int min = 1000;
            int randomNumber = rand.nextInt((max - min) + 1) + min;
            log.info("Generating  orderId {}", randomNumber);
            String numberPart = String.valueOf(randomNumber);
            log.info("Generated number part for orderId - {}", numberPart);
            String fullPart = "Order" + "-" + numberPart;
            log.info("Generated orderId - {}", fullPart);
            return fullPart;

        }
        catch (Exception e) {
            log.error("Error Generating orderId : {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
