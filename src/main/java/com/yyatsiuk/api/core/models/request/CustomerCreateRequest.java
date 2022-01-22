package com.yyatsiuk.api.core.models.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CustomerCreateRequest {

    @NotBlank(message = "Full name cannot be null or empty")
    private String fullName;
    @NotBlank(message = "Instagram cannot be null or empty")
    private String instagram;
    @NotBlank(message = "Phone cannot be null or empty")
    private String phone;
    private String address;

}
