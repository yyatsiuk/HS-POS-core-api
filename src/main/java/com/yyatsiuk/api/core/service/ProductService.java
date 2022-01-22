package com.yyatsiuk.api.core.service;

import com.yyatsiuk.api.core.models.dto.ProductDto;

import java.util.List;

public interface ProductService {

    Long save(ProductDto productDto);

    ProductDto update(ProductDto productDto);

    ProductDto findById(Long id);

    List<ProductDto> getAll();

    List<String> getAllCategories();

    void delete(Long id);

}
