package com.yyatsiuk.api.core.models.dto;

import com.yyatsiuk.api.core.enumerations.OrderStatus;
import com.yyatsiuk.api.core.enumerations.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long id;
    private List<LineItemDto> lineItems;
    private CustomerDto customer;
    private OrderStatus status;
    private PaymentStatus paymentStatus;
    private String address;
    private String courier;
    private Integer branchNumber;
    private String trackingCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
