package com.helium.oakcollectionsadmin.serviceImpls;

import com.helium.oakcollectionsadmin.dto.*;
import com.helium.oakcollectionsadmin.entity.UserInfo;
import com.helium.oakcollectionsadmin.enums.Roles;
import com.helium.oakcollectionsadmin.enums.isActive;
import com.helium.oakcollectionsadmin.exceptions.InvalidCredentialsException;
import com.helium.oakcollectionsadmin.jwt.JwtUtil;
import com.helium.oakcollectionsadmin.repository.UserInfoRepo;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OnboardingService {
    private final UserInfoRepo userInfoRepo;
    private final IdGenerationService userIdGenerationService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public ResponseEntity<GeneralResponse> signUp(SignUpRequest signUpRequest) {
        log.info("SignUp Process Has started");
        log.info("Sign up request:::: {}", signUpRequest);
        try {
            Optional<UserInfo> duplicateCheck = userInfoRepo.findByEmail(signUpRequest.getEmail());
            if (duplicateCheck.isPresent()) {
                return new ResponseEntity<>(new GeneralResponse("This User Has Already been registered", LocalDateTime.now().toString()), HttpStatus.CONFLICT);
            }

            UserInfo userInfo = new UserInfo();
            userInfo.setId(userIdGenerationService.UserIdGeneration(signUpRequest.getEmail()));
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

    public ResponseEntity<AuthenticationResponse> LogIn(LogInRequest logInRequest) {
        log.info("LogIn Process Has started");
        log.info("LogIn request::::::::::::: {}", logInRequest);

        Optional<UserInfo> doesUserExist = userInfoRepo.findByEmail(logInRequest.getEmail());

        if (doesUserExist.isEmpty()) {
            throw new InvalidCredentialsException();
        }

        UserInfo getAcct = doesUserExist.get();
        if (!passwordEncoder.matches(logInRequest.getPassword(), getAcct.getPassword())) {
            throw new InvalidCredentialsException();
        }

        String jwtToken = jwtUtil.generateToken(getAcct);
        ResponseCookie cookie = ResponseCookie.from("jwt", jwtToken)
                .httpOnly(true)
                .secure(true) // set to false for local dev if needed
                .path("/")
                .maxAge(24 * 60 * 60)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new AuthenticationResponse(jwtToken, "You have logged in successfully"));
    }

    public ResponseEntity<GeneralResponse> LogOut(HttpServletResponse response) {
        log.info("LogOut Process Has started");
        log.info("LogOut request::::::::::::: {}", LocalDateTime.now().toString());

        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(false) //true in production
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();
        response.setHeader("Set-Cookie", cookie.toString());
        return new ResponseEntity<>(new GeneralResponse("You have logged out", LocalDateTime.now().toString()), HttpStatus.OK);

    }

    public ResponseEntity<GeneralResponse> deleteAcct(DeleteAcctRequest deleteAcctRequest) {
        log.info("Account Deletion Has Started");
        log.info("Delete request:::::::::::::::: -- {}", deleteAcctRequest);


        Optional <UserInfo> userExist = userInfoRepo.findByEmail(deleteAcctRequest.getEmail());
        if (userExist.isPresent()) {
            log.info("Email Provided exists::::::::::::::::: {}", deleteAcctRequest.getEmail());
            log.info("About to delete user : {}", deleteAcctRequest.getEmail());
            userInfoRepo.delete(userExist.get());
            log.info("User with email : {} has been deleted", deleteAcctRequest.getEmail());
            return new ResponseEntity<>(new GeneralResponse("Account Deleted Successfully", LocalDateTime.now().toString()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new GeneralResponse("Email provided is not tied to a user", LocalDateTime.now().toString()), HttpStatus.BAD_REQUEST);
    }



}
