package com.geekbrains.products.repositories;

import contract.entities.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
	
	@Transactional
	List<Category> findAll();
	
	@Transactional
	Optional<Category> findById(Long id);
}
