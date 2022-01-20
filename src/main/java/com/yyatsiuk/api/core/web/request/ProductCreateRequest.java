package com.yyatsiuk.api.core.web.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yyatsiuk.api.core.enumerations.ProductStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class ProductCreateRequest {

    @NotBlank(message = "Product name must not be null")
    private String name;

    private String description;

    @NotBlank(message = "Image url must not be null or empty")
    private String imageUrl;

    @NotNull(message = "Price must not be null")
    private BigDecimal price;

    @NotNull(message = "Product status must not be null")
    @JsonProperty("status")
    private ProductStatus status;

    @NotBlank(message = "Product category must not be null or empty")
    @JsonProperty("category")
    private String category;

}
