package com.helium.oakcollectionsadmin.serviceImpls;

import com.helium.oakcollectionsadmin.dto.CustomerRequest;
import com.helium.oakcollectionsadmin.dto.GeneralResponse;
import com.helium.oakcollectionsadmin.dto.UpdateCustomerRequest;
import com.helium.oakcollectionsadmin.dto.fetchCustomerRequest;
import com.helium.oakcollectionsadmin.entity.CustomerModule;
import com.helium.oakcollectionsadmin.entity.OrderTracker;
import com.helium.oakcollectionsadmin.repository.CustomerRepo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Data
@Slf4j

public class CustomerOperations {
    private final IdGenerationService trackingId;
    private final CustomerRepo repo;



    public GeneralResponse createCustomer(CustomerRequest request) {
        try {

            log.info("createCustomer process has begun");

            CustomerModule customerModule = new CustomerModule();
            customerModule.setCustomerId(trackingId.UserIdGeneration(request.getCustomerEmail()));

            customerModule.setCustomerName(request.getCustomerName());
            customerModule.setCustomerEmail(request.getCustomerEmail());
            customerModule.setCustomerAddress(request.getCustomerAddress());
            customerModule.setCustomerPhoneNumber(request.getCustomerPhoneNumber());
            customerModule.setCustomerFirstName(request.getCustomerFirstName());
            customerModule.setCustomerLastName(request.getCustomerLastName());
            repo.save(customerModule);
            return new GeneralResponse("Successfully created customer ", LocalDateTime.now().toString());
        } catch (Exception e) {
            log.error("AN ERROR OCCURRED - {}", e.getMessage());
            throw new RuntimeException(e);
        }

    }

    public GeneralResponse updateCustomer(UpdateCustomerRequest request) {
        try {
            log.info("updateCustomer process has begun");
            Optional <CustomerModule> findByNumber = repo.findByCustomerPhoneNumber(request.getCustomerPhoneNumber());
            if (findByNumber.isEmpty()) {
                return new GeneralResponse("Customer not found", LocalDateTime.now().toString());
            }
            CustomerModule customerModule = findByNumber.get();
            if(request.getCustomerPhoneNumber()!=null){
                customerModule.setCustomerPhoneNumber(request.getCustomerPhoneNumber());
            }
            if(request.getCustomerFirstName()!=null){
                customerModule.setCustomerFirstName(request.getCustomerFirstName());
            }
            if(request.getCustomerLastName()!=null){
                customerModule.setCustomerLastName(request.getCustomerLastName());
            }
            if(request.getCustomerAddress()!=null){
                customerModule.setCustomerAddress(request.getCustomerAddress());
            }
            if(request.getCustomerName()!=null){
                customerModule.setCustomerName(request.getCustomerName());
            }
            if(request.getCustomerEmail()!=null){
                customerModule.setCustomerEmail(request.getCustomerEmail());
            }
            repo.save(customerModule);
            return new GeneralResponse("Successfully updated customer ", LocalDateTime.now().toString());
        }
        catch (Exception e) {
            log.error("AN ERROR OCCURRED WHILE PERFORMING CUSTOMER UPDATE - {}", e.getMessage());
            throw new RuntimeException(e);
        }

    }

    public Object fetchCustomer(fetchCustomerRequest request) {
        log.info("fetchCustomer process has begun");
        if (request.getPhoneNumber()==null) {
            return repo.findAll();
        }
        return repo.findByCustomerPhoneNumber(request.getPhoneNumber());
    }

    public GeneralResponse deleteCustomer(fetchCustomerRequest request) {
        try {
            log.info("deleteCustomer process has begun");
            Optional<CustomerModule> accountCheck = repo.findByCustomerPhoneNumber(request.getPhoneNumber());
            if (accountCheck.isPresent()) {
                repo.delete(accountCheck.get());
                return  new GeneralResponse( "Successfully deleted customer ", LocalDateTime.now().toString());
            }
            throw new RuntimeException("Customer not found");
        }
    catch (Exception e) {
            log.error("AN ERROR OCCURRED WHILE DELETING CUSTOMER - {}", e.getMessage());
        throw new RuntimeException("An error occurred while deleting customer - " + e.getMessage());
        }
    }

}
