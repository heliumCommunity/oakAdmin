package com.helium.oakcollectionsadmin.controller;

import com.helium.oakcollectionsadmin.dto.*;
import com.helium.oakcollectionsadmin.entity.OrderTracker;
import com.helium.oakcollectionsadmin.enums.status;
import com.helium.oakcollectionsadmin.serviceImpls.OnboardingService;
import com.helium.oakcollectionsadmin.serviceImpls.OrderPopulation;
import com.helium.oakcollectionsadmin.serviceImpls.UserInfoAuditService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/oakcollectionsadmin")
@RequiredArgsConstructor
@Slf4j
public class OakAdminController {

    private final UserInfoAuditService auditService;
    private final OnboardingService onboardingService;
    private final OrderPopulation orderPopulation;

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
    @PostMapping("admin/populate-orders")
    public ResponseEntity<GeneralResponse> populateOrders(@RequestBody OrderRequest orderRequest) {
        log.info("populateOrders has been called::::::");
        return orderPopulation.populateOrders(orderRequest);
    }
    @GetMapping("admin/get-all-orders")
    public List<OrderTracker> getAllOrders() {
        log.info("getAllOrders has been called::::::");
        return orderPopulation.getAllOrders();
    }
    @GetMapping("admin/get-orders-by-status")
    public OrderTracker getOrdersByStatus(@RequestBody GetOrderRequest status) {
        log.info("getOrdersByStatus has been called::::::");
        return orderPopulation.getAllOrdersByStatus(status);
    }
    @GetMapping("/admin/get-order-by-customer-name")
    public OrderTracker getOrdersByCustomerName(@RequestBody GetOrderByCustomerNameRequest customerName) {
        log.info("getOrdersByCustomerName has been called::::::");
        return orderPopulation.getAllOrdersByCustomerName(customerName);
    }
//    @GetMapping("/admin/get-order-by-customer-name")


}
