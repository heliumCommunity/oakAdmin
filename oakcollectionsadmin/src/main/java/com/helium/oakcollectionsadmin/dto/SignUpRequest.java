package com.helium.oakcollectionsadmin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpRequest {
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 30, message = "First name must be between 2 and 30 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 30, message = "Last name must be between 2 and 30 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=(?:.*\\d){2,})(?=.*[!@#$%^&*()_+{}\\[\\]:;<>,.?~\\-]).{8,}$",
            message = "Password must be at least 8 characters long, include at least 1 special character and 2 numbers")
    private String password;

    @Size(max = 30, message = "Middle name must be at most 30 characters")
    private String middleName; // Optional

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[\\d+]{10,15}$", message = "Phone number must be 10 to 15 digits")
    private String phoneNumber;

    @NotBlank(message = "Job title is required")
    @Size(max = 30, message = "Job title must be less than 30 characters")
    private String jobTitle;

    @NotBlank(message = "Role is required")
    private String role;


}
