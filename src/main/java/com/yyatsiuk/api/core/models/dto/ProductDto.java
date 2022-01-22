package com.yyatsiuk.api.core.models.dto;

import com.yyatsiuk.api.core.enumerations.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long id;
    private String code;
    private String imageUrl;
    private String name;
    private String description;
    private BigDecimal price;
    private ProductStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String category;

}
