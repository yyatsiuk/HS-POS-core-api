package com.yyatsiuk.api.core.mappers;

import com.yyatsiuk.api.core.dto.ProductDto;
import com.yyatsiuk.api.core.entities.Product;
import com.yyatsiuk.api.core.web.request.ProductCreateRequest;
import com.yyatsiuk.api.core.web.request.ProductUpdateRequest;
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
