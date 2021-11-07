package com.geekbrains.products.controllers;

import contract.entities.Product;
import contract.entities.ProductDTO;
import contract.entities.RestPageImpl;
import org.springframework.web.bind.annotation.*;
import java.util.List;

public interface ProductController {
	@GetMapping("/getAllProducts")
	List<Product> getAllProducts();

	@GetMapping("/getProductById")
	Product getProductById(@RequestParam("id") Long id);

	@GetMapping("/getProductByTitle/{title}")
	Product getProductByTitle(@PathVariable("title") String title);

	@GetMapping("/getProductsWithPagingAndFiltering")
	RestPageImpl<Product> getProductsWithPagingAndFiltering(
		@RequestParam(value = "page") int page,
		@RequestParam(value = "pageSize") int pageSize,
		@RequestParam(value = "word", required = false) String word,
		@RequestParam(value = "min", required = false) Double min,
		@RequestParam(value = "max", required = false) Double max);

	@PostMapping("/saveProduct")
	void saveProduct(@RequestBody ProductDTO product);

	@PostMapping("/addImage")
	void addImage(@RequestParam String title, @RequestParam String imageName);
	
}
