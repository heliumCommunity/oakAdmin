package com.helium.oakcollectionsadmin.serviceImpls;

import com.helium.oakcollectionsadmin.dto.*;
import com.helium.oakcollectionsadmin.entity.CustomerModule;
import com.helium.oakcollectionsadmin.entity.OrderTracker;
import com.helium.oakcollectionsadmin.enums.OrderFulfillmentMethod;
import com.helium.oakcollectionsadmin.enums.status;
import com.helium.oakcollectionsadmin.repository.CustomerRepo;
import com.helium.oakcollectionsadmin.repository.OrderTrackerRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional

public class OrderPopulation {
    private final OrderTrackerRepo orderTrackerRepo;
    private final IdGenerationService trackingId;
    private final CustomerRepo customerRepo;

    public ResponseEntity<GeneralResponse> populateOrders(OrderRequest orderRequest) {
        try {

            log.info("Populating orders has started");

            OrderTracker orderTracker = new OrderTracker();
            CustomerModule customer = new CustomerModule();
            orderTracker.setOrderId(trackingId.orderIdGeneration());
            if(customerRepo.findByCustomerPhoneNumber(orderRequest.getCustomerPhoneNumber()).isEmpty()) {
                customer.setCustomerId(trackingId.UserIdGeneration(orderRequest.getCustomerEmail()));
                customer.setCustomerFirstName(orderRequest.getCustomerFirstName());
                customer.setCustomerLastName(orderRequest.getCustomerLastName());
                customer.setCustomerEmail(orderRequest.getCustomerEmail());
                customer.setCustomerPhoneNumber(orderRequest.getCustomerPhoneNumber());
                customer.setCustomerAddress(orderRequest.getCustomerAddress());
                customer.setOrderCount(customer.getOrderCount() + 1);
                customerRepo.save(customer);
            }
            orderTracker.setCustomerFirstName(orderRequest.getCustomerFirstName());
            orderTracker.setCustomerLastName(orderRequest.getCustomerLastName());
            orderTracker.setCustomerEmail(orderRequest.getCustomerEmail());
            orderTracker.setCustomerPhoneNumber(orderRequest.getCustomerPhoneNumber());
            orderTracker.setCustomerAddress(orderRequest.getCustomerAddress());
            customerRepo.orderCount(orderRequest.getCustomerPhoneNumber());


            orderTracker.setChest(orderRequest.getChest());
            orderTracker.setHip(orderRequest.getHip());
            if (orderRequest.getStatus().equalsIgnoreCase("completed")) {
                orderTracker.setStatus(status.COMPLETED);
            } else if (orderRequest.getStatus().equalsIgnoreCase("ongoing")) {
                orderTracker.setStatus(status.ONGOING);
            } else if (orderRequest.getStatus().equalsIgnoreCase("delivered")) {
                orderTracker.setStatus(status.DELIVERED);
            } else if (orderRequest.getStatus().equalsIgnoreCase("at-risk")) {
                orderTracker.setStatus(status.AT_RISK);
            } else if (orderRequest.getStatus().equalsIgnoreCase("past_due_date")) {
                orderTracker.setStatus(status.PAST_DUE_DATE);

            } else {
                return new ResponseEntity<>(new GeneralResponse("Please select a valid order Status", LocalDateTime.now().toString()), HttpStatus.BAD_REQUEST);
            }
            if (orderRequest.getOrderFulfillmentMethod().equalsIgnoreCase("DELIVERY")) {
                orderTracker.setOrderFulfillmentMethod(OrderFulfillmentMethod.DELIVERY);
            } else if (orderRequest.getOrderFulfillmentMethod().equalsIgnoreCase("CARRYOUT")) {
                orderTracker.setOrderFulfillmentMethod(OrderFulfillmentMethod.CARRYOUT);
            } else {
                return new ResponseEntity<>(new GeneralResponse("Please select a valid order Fulfillment Method", LocalDateTime.now().toString()), HttpStatus.BAD_REQUEST);
            }
            orderTracker.setNeck(orderRequest.getNeck());
            orderTracker.setCustomerName(orderRequest.getCustomerName());
            orderTracker.setInseam(orderRequest.getInseam());
            orderTracker.setOutseam(orderRequest.getOutSeam());
            orderTracker.setDueDate(orderRequest.getDueDate());
            orderTracker.setShoulderWidth(orderRequest.getShoulderWidth());
            orderTracker.setSleeveLength(orderRequest.getSleeveLength());
            orderTracker.setThigh(orderRequest.getThigh());
            orderTracker.setWaist(orderRequest.getWaist());
            orderTracker.setWrist(orderRequest.getWrist());
            //would set progress later



            orderTracker.setTrackingId(trackingId.trackingIdGeneration());
            orderTracker.setRiderName(orderRequest.getRiderName());
            orderTracker.setRiderPhoneNumber(orderTracker.getRiderPhoneNumber());
            orderTracker.setCustomMeasurement(orderRequest.getCustomMeasurement());
            orderTracker.setFittingRequired(orderRequest.getFittingRequired());
            orderTracker.setPriorityLevel(orderRequest.getPriorityLevel());
            orderTracker.setStartDate(orderRequest.getStartDate());
            orderTracker.setEndDate(orderRequest.getEndDate());
            orderTracker.setClientType(orderRequest.getClientType());
            orderTracker.setAdditionalFitNotes(orderRequest.getAdditionalFitNotes());
            orderTracker.setAdditionalNotes(orderRequest.getAdditionalNotes());
            orderTracker.setOrderType(orderRequest.getOrderType());
            orderTracker.setProductType(orderRequest.getProductType());
            orderTracker.setProductColor(orderRequest.getProductColor());


            orderTrackerRepo.save(orderTracker);
            customerRepo.save(customer);
            return new ResponseEntity<>(new GeneralResponse("Orders have been populated", LocalDateTime.now().toString()), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(new GeneralResponse("Error while populating orders ", LocalDateTime.now().toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public List<OrderTracker> getAllOrders() {
        log.info("Getting all orders");
        return orderTrackerRepo.findAll();

    }

    public OrderTracker getAllOrdersByStatus(GetOrderRequest request) {
        log.info("Getting all orders by status");
        return orderTrackerRepo.findByStatus(request.getStatus());

    }

    public OrderTracker getAllOrdersByCustomerName(GetOrderByCustomerNameRequest CustomerName) {
        log.info("Getting all orders by customer name");
        OrderTracker checkCustomerName = orderTrackerRepo.findByCustomerName(CustomerName.getCustomerName());
        if (checkCustomerName != null) {
            return orderTrackerRepo.findByCustomerName(CustomerName.getCustomerName());
        } else {
            throw new RuntimeException("Customer name not found");
        }

    }

    public ResponseEntity<GeneralResponse> updateOrders(UpdateRequest updateRequest) {
        log.info("Updating orders process has begun:::::: \n" + updateRequest);
        try {
            Optional<OrderTracker> findByOrderId = orderTrackerRepo.findByOrderId(updateRequest.getOrderId());
        if (findByOrderId.isEmpty()) {
            return new ResponseEntity<>(new GeneralResponse("Order not found", LocalDateTime.now().toString()), HttpStatus.NOT_FOUND);
        }
        OrderTracker order = findByOrderId.get();


                if (updateRequest.getDueDate() != null) {
                    order.setDueDate(updateRequest.getDueDate());
                }

                if (updateRequest.getCustomerFirstName() != null) {
                    order.setCustomerFirstName(updateRequest.getCustomerFirstName());
                }

                if (updateRequest.getCustomerLastName() != null) {
                    order.setCustomerLastName(updateRequest.getCustomerLastName());
                }

                if (updateRequest.getCustomerName() != null) {
                    order.setCustomerName(updateRequest.getCustomerName());
                }

                if (updateRequest.getCustomerEmail() != null) {
                    order.setCustomerEmail(updateRequest.getCustomerEmail());
                }

                if (updateRequest.getCustomerPhoneNumber() != null) {
                    order.setCustomerPhoneNumber(updateRequest.getCustomerPhoneNumber());
                }

                if (updateRequest.getCustomerAddress() != null) {
                    order.setCustomerAddress(updateRequest.getCustomerAddress());
                }

                if (updateRequest.getOrderFulfillmentMethod() != null) {
                    try {
                        OrderFulfillmentMethod method = OrderFulfillmentMethod.valueOf(
                                updateRequest.getOrderFulfillmentMethod().toUpperCase());
                        order.setOrderFulfillmentMethod(method);
                    } catch (IllegalArgumentException ex) {
                        return new ResponseEntity<>(
                                new GeneralResponse("Invalid order fulfillment method", LocalDateTime.now().toString()),
                                HttpStatus.BAD_REQUEST);
                    }
                }

                if (updateRequest.getStatus() != null) {
                    try {
                        status statusEnum = com.helium.oakcollectionsadmin.enums.status.valueOf(
                                updateRequest.getStatus().toUpperCase());
                        order.setStatus(statusEnum);
                    } catch (IllegalArgumentException ex) {
                        return new ResponseEntity<>(
                                new GeneralResponse("Invalid status value", LocalDateTime.now().toString()),
                                HttpStatus.BAD_REQUEST);
                    }
                }

                if (updateRequest.getProgress() != null) {
                    order.setProgress(updateRequest.getProgress());
                }

                if (updateRequest.getNeck() != null) {
                    order.setNeck(updateRequest.getNeck());
                }

                if (updateRequest.getShoulderWidth() != null) {
                    order.setShoulderWidth(updateRequest.getShoulderWidth());
                }

                if (updateRequest.getChest() != null) {
                    order.setChest(updateRequest.getChest());
                }

                if (updateRequest.getWaist() != null) {
                    order.setWaist(updateRequest.getWaist());
                }

                if (updateRequest.getHip() != null) {
                    order.setHip(updateRequest.getHip());
                }

                if (updateRequest.getSleeveLength() != null) {
                    order.setSleeveLength(updateRequest.getSleeveLength());
                }

                if (updateRequest.getInseam() != null) {
                    order.setInseam(updateRequest.getInseam());
                }

                if (updateRequest.getOutSeam() != null) {
                    order.setOutseam(updateRequest.getOutSeam());
                }

                if (updateRequest.getThigh() != null) {
                    order.setThigh(updateRequest.getThigh());
                }

                if (updateRequest.getWrist() != null) {
                    order.setWrist(updateRequest.getWrist());
                }

                if (updateRequest.getRiderName() != null) {
                    order.setRiderName(updateRequest.getRiderName());
                    log.info("Updating rider name");
                }

                if (updateRequest.getRiderNumber() != null) {
                    order.setRiderPhoneNumber(updateRequest.getRiderNumber());
                    log.info("Updating rider number");
                }

                log.info(order.toString());

                orderTrackerRepo.save(order);
                orderTrackerRepo.flush(); // ensure it's committed to DB


                log.info("Orders have been populated - {}", order);

                return new ResponseEntity<>(
                        new GeneralResponse("Order updated successfully", LocalDateTime.now().toString()),
                        HttpStatus.OK);
            }
        catch (Exception e) {
            log.error(e.getMessage());
//            return e.getMessage();
        }
        return null;
   }



    public String deleteOrder(OrderDeleteRequest deleteAccountRequest) {
        Optional<OrderTracker> accountCheck = orderTrackerRepo.findById(deleteAccountRequest.getOrderId());
        if (accountCheck.isPresent()) {
            orderTrackerRepo.delete(accountCheck.get());
            return "Order Deleted Successfully";
        }
        return "Order Does Not Exist.Please Verify Your OrderId ";
    }
}
