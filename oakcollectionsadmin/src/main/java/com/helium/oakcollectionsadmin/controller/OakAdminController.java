package com.helium.oakcollectionsadmin.controller;

import com.helium.oakcollectionsadmin.dto.*;
import com.helium.oakcollectionsadmin.entity.OrderTracker;
import com.helium.oakcollectionsadmin.serviceImpls.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/oakcollectionsadmin")
@RequiredArgsConstructor
@Slf4j
public class OakAdminController {

    private final UserInfoAuditService auditService;
    private final OnboardingService onboardingService;
    private final OrderPopulation orderPopulation;
    private final OrderAssignmentService assignmentService;
    private final CustomerOperations customerOperations;

    @GetMapping("/admin/get-user-history")
    public Object getOakCollectionsAdminHistory(@RequestHeader String userId) {
        log.info("get-user-history has been called::::::");
        return auditService.getAuditHistory(userId);
    }

    @PostMapping("/auth/sign-up")
    public ResponseEntity<GeneralResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        log.info("sign-up has been called::::::");
        return onboardingService.signUp(signUpRequest);
    }

    @PostMapping("/auth/log-in")
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

    @PostMapping("/admin/update-order-details")
    public ResponseEntity<GeneralResponse> updateOrderDetails(@RequestBody UpdateRequest request) {
        log.info("updateOrderDetails has been called::::::");
        return orderPopulation.updateOrders(request);
    }

    @DeleteMapping("/admin/delete-order")
    public String deleteOrder(@RequestBody OrderDeleteRequest request) {
        log.info("deleteOrder has been called::::::");
        return orderPopulation.deleteOrder(request);
    }

    @PostMapping("/admin/assign-orders")
    public ResponseEntity<GeneralResponse> assignOrders(@RequestBody OrderAssignmentRequest orderAssignmentRequest) {
        log.info("assignOrders has been called::::::");
        return assignmentService.assignOrders(orderAssignmentRequest);
    }

    @PutMapping("/update-order-status")
    public ResponseEntity<GeneralResponse> updateOrders(@RequestBody UpdateOrderStatusRequest updateOrderStatusRequest) {
        log.info("updateOrders has been called::::::");
        return assignmentService.updateOrderStatus(updateOrderStatusRequest);
    }

    @PostMapping("/admin/create-customer")
    public GeneralResponse createCustomer(@RequestBody CustomerRequest customerRequest) {
        log.info("createCustomer has been called::::::");
        return customerOperations.createCustomer(customerRequest);
    }

    @PostMapping("/admin/fetch-customers")
    public Object fetchCustomers(@RequestBody fetchCustomerRequest fetchCustomerRequest) {
        log.info("fetchCustomers has been called::::::");
        return customerOperations.fetchCustomer(fetchCustomerRequest);
    }

    @PutMapping("/admin/update-customer")
    public GeneralResponse updateCustomer(@RequestBody UpdateCustomerRequest customerRequest) {
        log.info("updateCustomer has been called::::::");
        return customerOperations.updateCustomer(customerRequest);
    }

    @DeleteMapping("/admin/delete-customer")
    public GeneralResponse deleteCustomer(@RequestBody fetchCustomerRequest fetchCustomerRequest) {
        log.info("deleteCustomer has been called::::::");
        return customerOperations.deleteCustomer(fetchCustomerRequest);
    }

}
