package com.yyatsiuk.api.core.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserDto {

    private Long id;
    private String email;
    private String fullName;
    private String avatarUrl;
    private String position;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
