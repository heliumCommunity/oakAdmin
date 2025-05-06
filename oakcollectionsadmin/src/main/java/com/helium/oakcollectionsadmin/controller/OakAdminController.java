package com.helium.oakcollectionsadmin.controller;

import com.helium.oakcollectionsadmin.dto.GeneralResponse;
import com.helium.oakcollectionsadmin.dto.LogInRequest;
import com.helium.oakcollectionsadmin.dto.SignUpRequest;
import com.helium.oakcollectionsadmin.serviceImpls.OnboardingService;
import com.helium.oakcollectionsadmin.serviceImpls.UserInfoAuditService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/oakcollectionsadmin")
@RequiredArgsConstructor
@Slf4j
public class OakAdminController {

    private final UserInfoAuditService auditService;
    private final OnboardingService onboardingService;

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/get-user-history")
    public List<Map<String, Object>> getOakCollectionsAdminHistory(@RequestHeader Long userId) {
        log.info("get-user-history has been called::::::");
        return auditService.getAuditHistory(userId);
    }
    @PostMapping("/auth/sign-up")
    public ResponseEntity<GeneralResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest){
        log.info("sign-up has been called::::::");
        return onboardingService.signUp(signUpRequest);
    }
    @GetMapping("/auth/log-in")
    public ResponseEntity<GeneralResponse> logIn(@RequestBody LogInRequest logInRequest) {
        log.info("log-in has been called::::::");
        return onboardingService.LogIn(logInRequest);
    }
}
