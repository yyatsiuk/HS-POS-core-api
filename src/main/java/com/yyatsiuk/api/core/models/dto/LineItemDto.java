package com.yyatsiuk.api.core.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LineItemDto {

    private Long productId;
    private String name;
    private String imageUrl;
    private Integer quantity;
    private String code;
    private BigDecimal unitAmount;
    private BigDecimal totalAmount;
    private BigDecimal discountAmount;

}
