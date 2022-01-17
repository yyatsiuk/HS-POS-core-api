package com.yyatsiuk.api.core.web.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class CustomerUpdateRequest {

    @NotBlank(message = "Full name cannot be null or empty")
    private String fullName;
    @NotBlank(message = "Instagram cannot be null or empty")
    private String instagram;
    @NotBlank(message = "Phone cannot be null or empty")
    private String phone;
    @NotNull(message = "Creation date cannot be null")
    private LocalDateTime createdAt;
    private String address;
}
