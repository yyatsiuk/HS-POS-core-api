package com.yyatsiuk.api.core.security.dto;

import com.yyatsiuk.api.core.enumerations.UserStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class SecuredUserDTO {

    private Long id;
    private String email;
    private char[] password;
    private String accessToken;
    private UserStatus status;
    private String fullName;
    private String avatarUrl;
    private String position;
    private List<String> roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
