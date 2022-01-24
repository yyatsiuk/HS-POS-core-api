package com.yyatsiuk.api.core.repository;

import com.yyatsiuk.api.core.models.entities.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "category")
    List<Product> findAll();

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "category")
    List<Product> findAllByIdIn(List<Long> ids);

}
