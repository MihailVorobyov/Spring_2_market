package com.geekbrains.products.controllers;

import com.geekbrains.products.services.ProductService;
import contract.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ProductControllerImpl implements ProductController {
	
	private ProductService productService;
	
	@Autowired
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	
	@Override
	public List<Product> getAllProducts() {
		return productService.getAllProducts();
	}

	@Override
	public List<Product> getAllProductsWithFilter(Specification<Product> productSpecs) {
		return productService.getAllProductsWithFilter(productSpecs);
	}

	@Override
	public Product getProductById(Long id) {
		return productService.getProductById(id);
	}

	@Override
	public Product getProductByTitle(String title) {
		return productService.getProductByTitle(title);
	}

//	@Override
//	public Page<Product> getAllProductsByPage(int pageNumber, int pageSize) {
//		return productService.getAllProductsByPage(pageNumber, pageSize);
//	}
//
//	@Override
//	public Page<Product> getProductsWithPagingAndFiltering(int pageNumber, int pageSize, Specification<Product> productSpecification) {
//		return productService.getProductsWithPagingAndFiltering(pageNumber, pageSize, productSpecification);
//	}
//
//	@Override
//	public boolean isProductWithTitleExists(String productTitle) {
//		return productService.isProductWithTitleExists(productTitle);
//	}
//
//	@Override
//	public void saveProduct(Product product) {
//		productService.saveProduct(product);
//	}
//
//	@Override
//	public void addImage(Product product, MultipartFile file) {
//		productService.addImage(product, file);
//	}
}
