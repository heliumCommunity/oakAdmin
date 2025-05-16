package com.helium.oakcollectionsadmin.controller;

import com.helium.oakcollectionsadmin.dto.*;
import com.helium.oakcollectionsadmin.serviceImpls.OnboardingService;
import com.helium.oakcollectionsadmin.serviceImpls.UserInfoAuditService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
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

    @GetMapping("/admin/get-user-history")
    public Object getOakCollectionsAdminHistory(@RequestHeader String userId) {
        log.info("get-user-history has been called::::::");
        return auditService.getAuditHistory(userId);
    }
    @PostMapping("/auth/sign-up")
    public ResponseEntity<GeneralResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest){
        log.info("sign-up has been called::::::");
        return onboardingService.signUp(signUpRequest);
    }
    @PostMapping ("/auth/log-in")
    public ResponseEntity<AuthenticationResponse> logIn(@RequestBody LogInRequest logInRequest) {
        log.info("log-in has been called::::::");
        return onboardingService.LogIn(logInRequest);
    }
    @PostMapping("/auth/log-out")
    public ResponseEntity<GeneralResponse> logout(HttpServletResponse response) {
        log.info("log-out has been called::::::");
        return onboardingService.LogOut(response);

    }
    @DeleteMapping("admin/delete-user-acct")
    public ResponseEntity<GeneralResponse> deleteUser(@RequestBody DeleteAcctRequest request) {
        log.info("deleteUser endpoint has been called::::::");
        return onboardingService.deleteAcct(request);
    }
}
