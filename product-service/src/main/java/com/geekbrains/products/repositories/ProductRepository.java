package com.geekbrains.products.repositories;

import contract.entities.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    
    @Transactional
    List<Product> findAll();
    
    @Transactional
    List<Product> findAll(Specification<Product> productSpecs);
    
    @Transactional
    Optional<Product> findById(Long id);
    
    @Transactional
    Product findOneByTitle(String title);

    
}
