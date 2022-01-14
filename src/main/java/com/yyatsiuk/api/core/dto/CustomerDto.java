package com.yyatsiuk.api.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    private Long id;
    private String fullName;
    private String instagram;
    private String address;
    private String phone;

}
