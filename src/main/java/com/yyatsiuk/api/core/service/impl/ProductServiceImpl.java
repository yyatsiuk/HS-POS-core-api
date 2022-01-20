package com.yyatsiuk.api.core.service.impl;

import com.yyatsiuk.api.core.dto.ProductDto;
import com.yyatsiuk.api.core.entities.Product;
import com.yyatsiuk.api.core.mappers.ProductMapper;
import com.yyatsiuk.api.core.repository.ProductRepository;
import com.yyatsiuk.api.core.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private static final String NULL_PRODUCT_ERROR_MESSAGE = "Product DTO cannot be null";

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository,
                              ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public Long save(ProductDto productDto) {
        Assert.notNull(productDto, NULL_PRODUCT_ERROR_MESSAGE);

        Product product = productMapper.fromDtoToEntity(productDto);
        return productRepository.save(product).getId();
    }

    @Override
    public ProductDto update(ProductDto productDto) {
        return null;
    }

    @Override
    public ProductDto findById(Long id) {
        return null;
    }

    @Override
    public List<ProductDto> getAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::fromEntityToDto)
                .toList();
    }

    @Override
    public void delete(Long id) {

    }
}
