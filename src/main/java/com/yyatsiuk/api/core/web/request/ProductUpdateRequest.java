package com.yyatsiuk.api.core.web.request;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @NotBlank(message = "Image url must not be null or empty")
    @JsonProperty("imageUrl")
    private String imageUrl;

    @NotNull(message = "Price must not be null")
    @JsonProperty("price")
    private BigDecimal price;

    @NotNull(message = "Product status must not be null")
    @JsonProperty("status")
    private ProductStatus status;

    @NotBlank(message = "Product category must not be null or empty")
    @JsonProperty("category")
    private String category;

    @NotNull(message = "Creation date cannot be null")
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

}
