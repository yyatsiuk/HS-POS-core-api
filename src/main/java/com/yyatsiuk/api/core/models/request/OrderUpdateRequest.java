package com.yyatsiuk.api.core.models.request;

import com.yyatsiuk.api.core.enumerations.OrderStatus;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class OrderUpdateRequest {

    @NotEmpty(message = "Address must not be null or empty")
    private String address;

    @NotEmpty(message = "Courier must not be null or empty")
    private String courier;

    @NotEmpty(message = "Branch number must not be null or empty")
    private String branchNumber;

    @NotEmpty(message = "Phone must not be null or empty")
    private String phone;

    @NotNull(message = "Order status must not be null")
    private OrderStatus status;

    private String notes;

}
