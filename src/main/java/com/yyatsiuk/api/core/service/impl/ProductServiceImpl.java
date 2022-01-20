package com.yyatsiuk.api.core.service.impl;

import com.yyatsiuk.api.core.dto.ProductDto;
import com.yyatsiuk.api.core.entities.Product;
import com.yyatsiuk.api.core.entities.ProductCategory;
import com.yyatsiuk.api.core.mappers.ProductMapper;
import com.yyatsiuk.api.core.repository.ProductCategoryRepository;
import com.yyatsiuk.api.core.repository.ProductRepository;
import com.yyatsiuk.api.core.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private static final String NULL_PRODUCT_ERROR_MESSAGE = "Product DTO cannot be null";

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository,
                              ProductCategoryRepository productCategoryRepository,
                              ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.productMapper = productMapper;
    }

    @Transactional
    @Override
    public Long save(ProductDto productDto) {
        Assert.notNull(productDto, NULL_PRODUCT_ERROR_MESSAGE);

        Product product = productMapper.fromDtoToEntity(productDto);
        productCategoryRepository.findByName(productDto.getCategory()).ifPresent(product::setCategory);
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
    public List<String> getAllCategories() {
        Iterable<ProductCategory> allCategories = productCategoryRepository.findAll();
        List<String> categories = new ArrayList<>();
        allCategories.forEach(category -> categories.add(category.getName()));

        return categories;
    }

    @Override
    public void delete(Long id) {

    }
}
