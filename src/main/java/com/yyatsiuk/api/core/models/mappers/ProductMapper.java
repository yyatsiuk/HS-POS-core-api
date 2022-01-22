package com.yyatsiuk.api.core.models.mappers;

import com.yyatsiuk.api.core.models.dto.ProductDto;
import com.yyatsiuk.api.core.models.entities.Product;
import com.yyatsiuk.api.core.models.request.ProductCreateRequest;
import com.yyatsiuk.api.core.models.request.ProductUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "category", expression = "java(entity.getCategory().getName())")
    ProductDto fromEntityToDto(Product entity);

    @Mapping(target = "category", ignore = true)
    Product fromDtoToEntity(ProductDto dto);

    ProductDto fromCreateRequestToDto(ProductCreateRequest request);

    ProductDto fromUpdateRequestToDto(ProductUpdateRequest request, Long id);

}
