package com.yyatsiuk.api.core.models.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class LineItemRequest {

    private Long productId;

    private Integer quantity;

    private BigDecimal discountAmount;

}
