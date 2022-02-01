package com.yyatsiuk.api.core.models.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderCreateRequest {

    private Long customerId;

    private List<LineItemRequest> items;

    private String address;

    private String courier;

    private String branchNumber;

    private BigDecimal prepaymentAmount;

    private String notes;

}
