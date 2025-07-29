package com.helium.oakcollectionsadmin.dto;

import lombok.*;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDTO {
    private Long id;
    private String username;
    private String email;
    private List<String> role;
}