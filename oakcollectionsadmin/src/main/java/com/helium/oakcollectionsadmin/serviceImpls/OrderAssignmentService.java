package com.helium.oakcollectionsadmin.serviceImpls;

import com.helium.oakcollectionsadmin.dto.GeneralResponse;
import com.helium.oakcollectionsadmin.dto.OrderAssignmentRequest;
import com.helium.oakcollectionsadmin.dto.UpdateOrderStatusRequest;
import com.helium.oakcollectionsadmin.entity.OrderAssignmentEntity;
import com.helium.oakcollectionsadmin.entity.OrderTracker;
import com.helium.oakcollectionsadmin.entity.StatusTracker;
import com.helium.oakcollectionsadmin.repository.OrderAssignmentRepo;
import com.helium.oakcollectionsadmin.repository.OrderTrackerRepo;
import com.helium.oakcollectionsadmin.repository.StatusTrackerRepo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderAssignmentService {
    private final OrderAssignmentRepo orderAssignmentRepo;
    private final OrderTrackerRepo trackerRepo;
    private final StatusTrackerRepo statusRepo;


    public ResponseEntity<GeneralResponse> assignOrders(OrderAssignmentRequest request){
        log.info("Order Id supplied is - {}", request.getOrderId());
        OrderAssignmentEntity orderAssignment = new OrderAssignmentEntity();


        OrderTracker findId = trackerRepo.findByOrderId(request.getOrderId()).orElseThrow(() -> new RuntimeException("Order Supplied does not exist"));
        log.info("Order Id exists as - {}", findId.getOrderId());


        orderAssignment.setOrderId(findId);
        orderAssignment.setAssembler(request.getAssembler());
        orderAssignment.setButtonholer(request.getButtonholer());
        orderAssignment.setCutter(request.getCutter());
        orderAssignment.setEmbroiderer(request.getEmbroiderer());
        orderAssignment.setFinisher(request.getFinisher());
        orderAssignment.setDesigner(request.getDesigner());
        orderAssignment.setPresser(request.getPresser());
        orderAssignment.setPacker(request.getPacker());
        orderAssignment.setTailor(request.getTailor());
        orderAssignment.setQualityChecker(request.getQualityChecker());
        orderAssignmentRepo.save(orderAssignment);

        StatusTracker statusTracker = new StatusTracker();
        statusTracker.setOrderId(findId);
        statusRepo.save(statusTracker);

        return new ResponseEntity<>(new GeneralResponse("Order Have been saved",LocalDateTime.now().toString()), HttpStatus.OK);


    }

    public ResponseEntity<GeneralResponse> updateOrderStatus(UpdateOrderStatusRequest request){


        Optional<StatusTracker> findId = Optional.ofNullable(statusRepo.findByOrderId(request.getOrderId()).orElseThrow(() -> new RuntimeException("Order Supplied does not exist")));

        StatusTracker statusTracker = findId.get();


//        StatusTracker statusTracker = new StatusTracker();


        if(request.getAssemblerStatus()!=null){
            statusTracker.setAssemblerStatus(request.getAssemblerStatus());
        }
        if(request.getButtonholerStatus()!=null){
            statusTracker.setButtonholerStatus(request.getButtonholerStatus());
        }
        if(request.getCutterStatus()!=null){
            statusTracker.setCutterStatus(request.getCutterStatus());
        }
        if(request.getEmbroidererStatus()!=null){
            statusTracker.setEmbroidererStatus(request.getEmbroidererStatus());
        }
        if(request.getFinisherStatus()!=null){
            statusTracker.setFinisherStatus(request.getFinisherStatus());
        }
        if(request.getDesignerStatus()!=null){
            statusTracker.setDesignerStatus(request.getDesignerStatus());
        }
        if(request.getPresserStatus()!=null){
            statusTracker.setPresserStatus(request.getPresserStatus());
        }
        if(request.getPackerStatus()!=null){
            statusTracker.setPackerStatus(request.getPackerStatus());
        }
        if(request.getTailorStatus()!=null){
            statusTracker.setTailorStatus(request.getTailorStatus());
        }
        if(request.getQualityCheckerStatus()!=null){
            statusTracker.setQualityCheckerStatus(request.getQualityCheckerStatus());
        }
        log.info("OrderStatus is About to be updated");
        statusRepo.save(statusTracker);
        log.info("OrderStatus is updated");

        return new ResponseEntity<>(new GeneralResponse("Order Updated Successfully",LocalDateTime.now().toString()), HttpStatus.OK);


    }








}
