package com.yyatsiuk.api.core.service.impl;

import com.yyatsiuk.api.core.models.dto.ProductDto;
import com.yyatsiuk.api.core.models.entities.Product;
import com.yyatsiuk.api.core.models.entities.ProductCategory;
import com.yyatsiuk.api.core.exceptions.EntityNotFoundException;
import com.yyatsiuk.api.core.models.mappers.ProductMapper;
import com.yyatsiuk.api.core.repository.ProductCategoryRepository;
import com.yyatsiuk.api.core.repository.ProductRepository;
import com.yyatsiuk.api.core.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.yyatsiuk.api.core.models.entities.Product.CODE_LENGTH;
import static com.yyatsiuk.api.core.utils.CodeGenerator.generateRandomCode;

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
        product.setCode(generateRandomCode(productDto.getName(), CODE_LENGTH));
        productCategoryRepository.findByName(productDto.getCategory()).ifPresent(product::setCategory);
        return productRepository.save(product).getId();
    }

    @Override
    public ProductDto update(ProductDto productDto) {
        Assert.notNull(productDto, NULL_PRODUCT_ERROR_MESSAGE);

        Product product = productMapper.fromDtoToEntity(productDto);
        productCategoryRepository.findByName(productDto.getCategory()).ifPresent(product::setCategory);
        Product productUpdated = productRepository.save(product);

        return productMapper.fromEntityToDto(productUpdated);
    }

    @Override
    public ProductDto findById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::fromEntityToDto)
                .orElseThrow(() -> new EntityNotFoundException("Product with id: {0} not found", id));
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
        productRepository.deleteById(id);
    }

}
