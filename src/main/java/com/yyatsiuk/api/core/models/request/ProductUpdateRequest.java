package com.yyatsiuk.api.core.models.request;

import com.yyatsiuk.api.core.enumerations.ProductStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProductUpdateRequest {

    @NotBlank(message = "Product name must not be null")
    private String name;

    @NotBlank(message = "Product name must not be null")
    private String code;

    private String description;

    @NotBlank(message = "Image url must not be null or empty")
    private String imageUrl;

    @NotNull(message = "Price must not be null")
    private BigDecimal price;

    @NotNull(message = "Product status must not be null")
    private ProductStatus status;

    @NotBlank(message = "Product category must not be null or empty")
    private String category;

    @NotNull(message = "Creation date cannot be null")
    private LocalDateTime createdAt;

}
