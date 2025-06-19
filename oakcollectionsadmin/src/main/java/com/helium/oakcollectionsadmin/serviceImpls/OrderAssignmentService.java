package com.helium.oakcollectionsadmin.serviceImpls;

import com.helium.oakcollectionsadmin.dto.GeneralResponse;
import com.helium.oakcollectionsadmin.dto.OrderAssignmentRequest;
import com.helium.oakcollectionsadmin.dto.RoleAssignmentRequest;
import com.helium.oakcollectionsadmin.dto.UpdateOrderStatusRequest;
import com.helium.oakcollectionsadmin.entity.*;
import com.helium.oakcollectionsadmin.enums.JobTitle;
import com.helium.oakcollectionsadmin.repository.*;
import jakarta.persistence.PersistenceException;
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
    private final RoleTableRepo roleTableRepo;
    private final IdGenerationService idGenerationService;
    private final UserInfoRepo userInfoRepo;




    public ResponseEntity<GeneralResponse> assignOrders(OrderAssignmentRequest request){
        try {

            log.info("Order Id supplied is - {}", request.getOrderId());
            OrderAssignmentEntity orderAssignment = new OrderAssignmentEntity();
            RoleTable roleTable = new RoleTable();


            OrderTracker findId = trackerRepo.findByOrderId(request.getOrderId()).orElseThrow(() -> new RuntimeException("Order Supplied does not exist"));
            log.info("Order Id exists as - {}", findId.getOrderId());

            orderAssignment.setOrderId(findId);
            if (!(request.getAssembler()==null)) {
                orderAssignment.setAssembler(request.getAssembler());
                roleTable.setRoleName(JobTitle.assembler);
                roleTable.setRoleId(idGenerationService.RoleIdGeneration("ASSEMBLER"));
                roleTable.setOrderId(request.getOrderId());
                roleTable.setStaffId(userInfoRepo.findByStaffId(request.getAssembler().trim()));
            }


            if (!(request.getCutter()==null)) {
                orderAssignment.setCutter(request.getCutter());
                roleTable.setRoleName(JobTitle.cutter);
                roleTable.setRoleId(idGenerationService.RoleIdGeneration("CUTTER"));
                roleTable.setOrderId(request.getOrderId());
                roleTable.setStaffId(userInfoRepo.findByStaffId(request.getCutter().trim()));
            }

            if (!(request.getEmbroiderer()==null)) {
                orderAssignment.setEmbroiderer(request.getEmbroiderer());
                roleTable.setRoleName(JobTitle.embroiderer);
                roleTable.setRoleId(idGenerationService.RoleIdGeneration("EMBROIDER"));
                roleTable.setOrderId(request.getOrderId());
                roleTable.setStaffId(userInfoRepo.findByStaffId(request.getEmbroiderer().trim()));
            }
            if (!(request.getFinisher()==null)) {
                orderAssignment.setFinisher(request.getFinisher());
                roleTable.setRoleName(JobTitle.finisher);
                roleTable.setRoleId(idGenerationService.RoleIdGeneration("FINISHER"));
                roleTable.setOrderId(request.getOrderId());
                roleTable.setStaffId(userInfoRepo.findByStaffId(request.getFinisher().trim()));
            }
            if (!(request.getDesigner()==null)) {
                orderAssignment.setDesigner(request.getDesigner());
                roleTable.setRoleName(JobTitle.designer);
                roleTable.setRoleId(idGenerationService.RoleIdGeneration("DESIGNER"));
                roleTable.setOrderId(request.getOrderId());
                roleTable.setStaffId(userInfoRepo.findByStaffId(request.getDesigner().trim()));
            }
            if (!(request.getPresser()==null )) {
                orderAssignment.setPresser(request.getPresser());
                roleTable.setRoleName(JobTitle.presser);
                roleTable.setRoleId(idGenerationService.RoleIdGeneration("PRESSER"));
                roleTable.setOrderId(request.getOrderId());
                roleTable.setStaffId(userInfoRepo.findByStaffId(request.getPresser().trim()));
            }
            if (!(request.getButtonholer()==null)) {
                orderAssignment.setButtonholer(request.getButtonholer());
                roleTable.setRoleName(JobTitle.buttonholer);
                roleTable.setRoleId(idGenerationService.RoleIdGeneration("BUTTONHOLER"));
                roleTable.setOrderId(request.getOrderId());
                roleTable.setStaffId(userInfoRepo.findByStaffId(request.getButtonholer().trim()));
            }
            if (!(request.getPacker()==null)) {
                orderAssignment.setPacker(request.getPacker());
                roleTable.setRoleName(JobTitle.packer);
                roleTable.setRoleId(idGenerationService.RoleIdGeneration("PACKER"));
                roleTable.setOrderId(request.getOrderId());
                roleTable.setStaffId(userInfoRepo.findByStaffId(request.getPacker().trim()));
            }
            if (!(request.getQualityChecker()==null)) {
                orderAssignment.setQualityChecker(request.getQualityChecker());
                roleTable.setRoleName(JobTitle.qualityChecker);
                roleTable.setRoleId(idGenerationService.RoleIdGeneration("QUALITYCHECKER"));
                roleTable.setOrderId(request.getOrderId());
                roleTable.setStaffId(userInfoRepo.findByStaffId(request.getQualityChecker().trim()));
            }

            if (!(request.getTailor()==null)) {
                orderAssignment.setTailor(request.getTailor());
                roleTable.setRoleName(JobTitle.tailor);
                roleTable.setRoleId(idGenerationService.RoleIdGeneration("TAILOR"));
                roleTable.setOrderId(request.getOrderId());
                roleTable.setStaffId(userInfoRepo.findByStaffId(request.getTailor().trim()));
            }
            orderAssignmentRepo.save(orderAssignment);
            roleTableRepo.save(roleTable);

            StatusTracker statusTracker = new StatusTracker();
            statusTracker.setOrderId(findId);
            statusRepo.save(statusTracker);


            return new ResponseEntity<>(new GeneralResponse("Order Have been saved", LocalDateTime.now().toString()), HttpStatus.OK);
        }
        catch (PersistenceException e){
            log.info("\n ERROR Persisting DATA INTO DB,BECAUSE OF -{} AND", e.getMessage(),e.getCause());
            throw new PersistenceException(e);
        }
        catch (Exception e){
            log.info("\n AN ERROR OCCURRED -{}  ",e.getMessage(),e.getCause());
            throw new RuntimeException(e.getMessage());
        }


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
