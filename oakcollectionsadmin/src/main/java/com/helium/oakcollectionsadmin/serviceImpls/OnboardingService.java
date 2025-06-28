package com.helium.oakcollectionsadmin.serviceImpls;

import com.helium.oakcollectionsadmin.dto.*;
import com.helium.oakcollectionsadmin.entity.UserInfo;
import com.helium.oakcollectionsadmin.enums.JobTitle;
import com.helium.oakcollectionsadmin.enums.Roles;
import com.helium.oakcollectionsadmin.enums.isActive;
import com.helium.oakcollectionsadmin.exceptions.InvalidCredentialsException;
import com.helium.oakcollectionsadmin.jwt.JwtUtil;
import com.helium.oakcollectionsadmin.repository.UserInfoRepo;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Role;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class OnboardingService {
    private final UserInfoRepo userInfoRepo;
    private final IdGenerationService userIdGenerationService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public ResponseEntity<GeneralResponse> signUp(SignUpRequest signUpRequest) {/*,OrderAssignmentRequest orderAssignmentRequest) {*/
        log.info("SignUp Process Has started");
        log.info("Sign up request:::: {}", signUpRequest);
        try {
            String jobTitle = signUpRequest.getJobTitle();

            Optional<UserInfo> duplicateCheck = userInfoRepo.findByEmail(signUpRequest.getEmail());
            if (duplicateCheck.isPresent()) {
                return new ResponseEntity<>(new GeneralResponse("This User Has Already been registered", LocalDateTime.now().toString()), HttpStatus.CONFLICT);
            }


            UserInfo userInfo = new UserInfo();
            userInfo.setStaffId(userIdGenerationService.UserIdGeneration(signUpRequest.getEmail()));
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


            if (jobTitle != null) {
                switch (jobTitle.toLowerCase()) {
                    case "cutter":
                        userInfo.setDesignation(JobTitle.cutter);
                        break;
                    case "tailor":
                        userInfo.setDesignation(JobTitle.tailor);
                        break;
                    case "designer":
                        userInfo.setDesignation(JobTitle.designer);
                        break;
                    case "assembler":
                        userInfo.setDesignation(JobTitle.assembler);
                        break;
                    case "embroiderer":
                        userInfo.setDesignation(JobTitle.embroiderer);
                        break;
                    case "buttonholer":
                        userInfo.setDesignation(JobTitle.buttonholer);
                        break;
                    case "presser":
                        userInfo.setDesignation(JobTitle.presser);
                        break;
                    case "finisher":
                        userInfo.setDesignation(JobTitle.finisher);
                        break;
                    case "qualitychecker":
                        userInfo.setDesignation(JobTitle.qualityChecker);
                        break;
                    case "packer":
                        userInfo.setDesignation(JobTitle.packer);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid job title: " + jobTitle);
                }
            }
                userInfo.setPhoneNumber(signUpRequest.getPhoneNumber());
                userInfo.setEmail(signUpRequest.getEmail());
                userInfo.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
                userInfoRepo.save(userInfo);
                log.info("SignUp Process Has finished");
                return new ResponseEntity<>(new GeneralResponse("Your Profile Has been created successfully", LocalDateTime.now().toString()), HttpStatus.OK);
            } catch(Exception e){
                log.error("Error is - {}", e.getMessage());
                return new ResponseEntity<>(new GeneralResponse(e.getMessage(), LocalDateTime.now().toString()), HttpStatus.INTERNAL_SERVER_ERROR);
            }

    }


    public ResponseEntity<AuthenticationResponse> LogIn(LogInRequest logInRequest) {
        log.info("LogIn Process Has started");
        log.info("LogIn request::::::::::::: {}", logInRequest);

            Optional<UserInfo> doesUserExist = userInfoRepo.findByEmail(logInRequest.getEmail());
            if(doesUserExist.isPresent()) {
                UserInfo getAcct = doesUserExist.get();
                if (passwordEncoder.matches(logInRequest.getPassword(), getAcct.getPassword())) {
                    String jwtToken = jwtUtil.generateToken(getAcct);
                    ResponseCookie cookie = ResponseCookie.from("jwt", jwtToken)
                            .httpOnly(true)
                            .secure(true) // in production
                            .path("/")
                            .maxAge(24 * 60 * 60)
                            .build();

                    Collection<? extends GrantedAuthority> authorities = getAcct.getAuthorities();
                    List<String> roles = authorities.stream()
                            .map(authority -> "ROLE_" + authority.getAuthority())
                            .collect(Collectors.toList());

                    // Construct user DTO
                    UserInfoDTO userDto = new UserInfoDTO(
                            getAcct.getId(),
                            getAcct.getUsername(),
                            getAcct.getEmail(),
                            roles
                    );
                    List<Object> responseDetails = new ArrayList<>();
                    responseDetails.add(userDto);
                    return ResponseEntity.ok()
                            .header(HttpHeaders.SET_COOKIE, cookie.toString())
                            .body(new AuthenticationResponse(jwtToken, "You have logged in successfully",responseDetails
                                    ));

                }
                return  ResponseEntity.ok()
                        .body(new AuthenticationResponse("Invalid Email or Password", LocalDateTime.now().toString(),null));
                }

        throw new InvalidCredentialsException();
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
