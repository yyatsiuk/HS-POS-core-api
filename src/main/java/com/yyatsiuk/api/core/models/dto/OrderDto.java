package com.yyatsiuk.api.core.models.dto;

import com.yyatsiuk.api.core.enumerations.OrderStatus;
import com.yyatsiuk.api.core.enumerations.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private String id;
    private List<LineItemDto> lineItems;
    private BigDecimal prepaymentAmount;
    private BigDecimal subtotalAmount;
    private BigDecimal totalAmount;
    private CustomerDto customer;
    private OrderStatus status;
    private PaymentStatus paymentStatus;
    private String address;
    private String courier;
    private String branchNumber;
    private String trackingCode;
    private String note;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
