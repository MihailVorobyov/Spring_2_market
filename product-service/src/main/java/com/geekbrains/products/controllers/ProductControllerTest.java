package com.geekbrains.products.controllers;

import com.geekbrains.products.services.ProductService;
import contract.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProductControllerTest {
	
	@Autowired
	ProductService productService;
	
	@GetMapping("/getProduct")
	@ResponseBody
	public Product getProduct() {
		
		Product product = productService.getProductById(1L);
		return product;
	}
}
