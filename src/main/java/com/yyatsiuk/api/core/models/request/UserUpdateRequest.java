package com.yyatsiuk.api.core.models.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
public class UserUpdateRequest {

    @NotBlank(message = "User full name should not be null or empty")
    private String fullName;

    @Email
    @NotBlank(message = "User email should not be null or empty")
    private String email;

    private String position;

    private String avatarUrl;

    private LocalDateTime createdAt;

}
