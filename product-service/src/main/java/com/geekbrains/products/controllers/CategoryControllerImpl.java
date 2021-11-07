package com.geekbrains.products.controllers;

import com.geekbrains.products.services.CategoryService;
import contract.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryControllerImpl implements CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@Override
	public List<Category> getAllCategories() {
		return categoryService.getAllCategories();
	}
	
	@Override
	public Category getCategoryById(Long id) {
		return categoryService.getCategory(id);
	}
}
