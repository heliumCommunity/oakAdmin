package com.helium.oakcollectionsadmin.serviceImpls;

import com.helium.oakcollectionsadmin.dto.GeneralResponse;
import com.helium.oakcollectionsadmin.dto.GetOrderByCustomerNameRequest;
import com.helium.oakcollectionsadmin.dto.GetOrderRequest;
import com.helium.oakcollectionsadmin.dto.OrderRequest;
import com.helium.oakcollectionsadmin.entity.OrderTracker;
import com.helium.oakcollectionsadmin.enums.OrderFulfillmentMethod;
import com.helium.oakcollectionsadmin.enums.status;
import com.helium.oakcollectionsadmin.repository.OrderTrackerRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderPopulation {
    private final OrderTrackerRepo orderTrackerRepo;
    private final IdGenerationService trackingId;

    public ResponseEntity<GeneralResponse> populateOrders(OrderRequest orderRequest) {
        try {

            log.info("Populating orders has started");
            OrderTracker orderTracker = new OrderTracker();
            orderTracker.setCustomerFirstName(orderRequest.getCustomerFirstName());
            orderTracker.setCustomerLastName(orderRequest.getCustomerLastName());
            orderTracker.setCustomerEmail(orderRequest.getCustomerEmail());
            orderTracker.setCustomerPhoneNumber(orderRequest.getCustomerPhoneNumber());
            orderTracker.setCustomerAddress(orderRequest.getCustomerAddress());
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
                return new ResponseEntity<>(new GeneralResponse("Please select a valid order Status", LocalDateTime.now().toString()),HttpStatus.BAD_REQUEST);
            }
            if (orderRequest.getOrderFulfillmentMethod().equalsIgnoreCase("DELIVERY")) {
                orderTracker.setOrderFulfillmentMethod(OrderFulfillmentMethod.DELIVERY);
            } else if (orderRequest.getOrderFulfillmentMethod().equalsIgnoreCase("CARRYOUT")) {
                orderTracker.setOrderFulfillmentMethod(OrderFulfillmentMethod.CARRYOUT);
            } else {
                return  new ResponseEntity<>(new GeneralResponse("Please select a valid order Fulfillment Method", LocalDateTime.now().toString()),HttpStatus.BAD_REQUEST);
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
            orderTracker.setCustomerId(trackingId.UserIdGeneration(orderRequest.getCustomerEmail()));
            orderTracker.setTrackingId(trackingId.trackingIdGeneration());
            orderTracker.setRiderName(orderRequest.getRiderName());
            orderTracker.setRiderPhoneNumber(orderTracker.getRiderPhoneNumber());
            orderTrackerRepo.save(orderTracker);
            return new ResponseEntity<>(new GeneralResponse("Orders have been populated", LocalDateTime.now().toString()),HttpStatus.OK);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(new GeneralResponse("Error while populating orders ", LocalDateTime.now().toString()),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    public List<OrderTracker> getAllOrders(){
        log.info("Getting all orders");
        return orderTrackerRepo.findAll();

    }
    public OrderTracker getAllOrdersByStatus( GetOrderRequest request){
        log.info("Getting all orders by status");
       return orderTrackerRepo.findByStatus(request.getStatus());

    }
    public OrderTracker getAllOrdersByCustomerName( GetOrderByCustomerNameRequest CustomerName){
        log.info("Getting all orders by customer name");
        return orderTrackerRepo.findByCustomerName(CustomerName.getCustomerName());

    }
//    public ResponseEntity<GeneralResponse> getAllOrdersBy(String inseam){}
}
