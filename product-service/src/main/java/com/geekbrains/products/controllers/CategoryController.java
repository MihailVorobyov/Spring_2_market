package com.geekbrains.products.controllers;

import contract.entities.Category;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface CategoryController {
	
	@GetMapping("/getAllCategories")
	List<Category> getAllCategories();
	
	@GetMapping("/getCategory")
	Category getCategoryById(Long id);
}
