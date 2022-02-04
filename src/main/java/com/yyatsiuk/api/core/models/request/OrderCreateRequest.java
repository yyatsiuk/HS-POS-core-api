package com.yyatsiuk.api.core.models.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderCreateRequest {

    @NotNull(message = "Customer id must not be null or empty")
    private Long customerId;

    @NotEmpty(message = "Item list must not be null or empty")
    private List<LineItemRequest> items;

    @NotEmpty(message = "Address must not be null or empty")
    private String address;

    @NotEmpty(message = "Courier must not be null or empty")
    private String courier;

    @NotEmpty(message = "Branch number must not be null or empty")
    private String branchNumber;

    private BigDecimal prepaymentAmount;

    private String notes;

}
