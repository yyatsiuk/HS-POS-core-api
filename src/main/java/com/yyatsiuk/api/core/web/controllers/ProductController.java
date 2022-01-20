package com.yyatsiuk.api.core.web.controllers;

import com.yyatsiuk.api.core.dto.ProductDto;
import com.yyatsiuk.api.core.mappers.ProductMapper;
import com.yyatsiuk.api.core.service.ProductService;
import com.yyatsiuk.api.core.web.request.ProductCreateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/products")
@CrossOrigin("*")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService,
                             ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody @Valid ProductCreateRequest payload, HttpServletRequest req) {
        Long id = productService.save(productMapper.fromCreateRequestToDto(payload));
        return ResponseEntity.created(URI.create(req.getRequestURL()
                        .append("/")
                        .append(id)
                        .toString()))
                .build();
    }

    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        return ResponseEntity.ok(productService.getAllCategories());
    }

}
