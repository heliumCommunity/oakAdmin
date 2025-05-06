package com.helium.oakcollectionsadmin.serviceImpls;

import com.helium.oakcollectionsadmin.dto.GeneralResponse;
import com.helium.oakcollectionsadmin.dto.LogInRequest;
import com.helium.oakcollectionsadmin.dto.SignUpRequest;
import com.helium.oakcollectionsadmin.entity.UserInfo;
import com.helium.oakcollectionsadmin.enums.Roles;
import com.helium.oakcollectionsadmin.enums.isActive;
import com.helium.oakcollectionsadmin.repository.UserInfoRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OnboardingService {
    private final UserInfoRepo userInfoRepo;
    private final UserIdGenerationService userIdGenerationService;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<GeneralResponse> signUp(SignUpRequest signUpRequest) {
        log.info("SignUp Process Has started");
        log.info("Sign up request:::: {}", signUpRequest);
        try {
            Optional<UserInfo> duplicateCheck = userInfoRepo.findByEmail(signUpRequest.getEmail());
            if (duplicateCheck.isPresent()) {
                return new ResponseEntity<>(new GeneralResponse("This User Has Already been registered", LocalDateTime.now().toString()), HttpStatus.CONFLICT);
            }

            UserInfo userInfo = new UserInfo();
            userInfo.setId(userIdGenerationService.IdGeneration(signUpRequest.getEmail()));
            userInfo.setFirstName(signUpRequest.getFirstName());
            userInfo.setLastName(signUpRequest.getLastName());
            userInfo.setMiddleName(signUpRequest.getMiddleName());
            if (signUpRequest.getRole().equalsIgnoreCase("admin")) {
                userInfo.setRole(Roles.ADMIN);
            } else if (signUpRequest.getRole().equalsIgnoreCase("user")) {
                userInfo.setRole(Roles.USER);
            } else {
                return new ResponseEntity<>(new GeneralResponse("Select a Valid Role Between User and Admin", LocalDateTime.now().toString()), HttpStatus.BAD_REQUEST);
            }
            userInfo.setActivationStatus(isActive.NOT_ACTIVATED);
            userInfo.setDesignation(signUpRequest.getJobTitle());
            userInfo.setPhoneNumber(signUpRequest.getPhoneNumber());
            userInfo.setEmail(signUpRequest.getEmail());
            userInfo.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
            userInfoRepo.save(userInfo);
            log.info("SignUp Process Has finished");
            return new ResponseEntity<>(new GeneralResponse("Your Profile Has been created successfully", LocalDateTime.now().toString()), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error is - {}", e.getMessage());
            return new ResponseEntity<>(new GeneralResponse(e.getMessage(), LocalDateTime.now().toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<GeneralResponse> LogIn(LogInRequest logInRequest) {
        log.info("LogIn Process Has started");
        log.info("LogIn request::::::::::::: {}", logInRequest);
        try {
            Optional<UserInfo> doesUserExist = userInfoRepo.findByEmail(logInRequest.getEmail());
            if (doesUserExist.isPresent()) {

                if (passwordEncoder.matches(logInRequest.getPassword(), doesUserExist.get().getPassword())) {
                    return new ResponseEntity<>(new GeneralResponse("You have logged in successfully", LocalDateTime.now().toString()), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(new GeneralResponse("Invalid Email or Password", LocalDateTime.now().toString()), HttpStatus.BAD_REQUEST);
                }
            }

        } catch (Exception e) {
            log.error("Error is - {}", e.getMessage());
            return new ResponseEntity<>(new GeneralResponse(e.getMessage(), LocalDateTime.now().toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }

}
