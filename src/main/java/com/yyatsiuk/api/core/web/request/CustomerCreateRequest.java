package com.yyatsiuk.api.core.web.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerCreateRequest {

    private String fullName;
    private String instagram;
    private String address;
    private String phone;

}
