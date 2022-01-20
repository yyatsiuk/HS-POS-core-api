package com.yyatsiuk.api.core.service;

import com.yyatsiuk.api.core.dto.ProductDto;

import java.util.List;

public interface ProductService {

    Long save(ProductDto productDto);

    ProductDto update(ProductDto productDto);

    ProductDto findById(Long id);

    List<ProductDto> getAll();

    void delete(Long id);

}
