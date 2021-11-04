package services;

import contract.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.cloud.openfeign.FeignClient;


import java.util.List;

@FeignClient("product-service-client")
@Service
public interface ProductService {
	@GetMapping("/getAllProducts")
	List<Product> getAllProducts();
	
	@PostMapping("/getAllProductsWithFilter")
	List<Product> getAllProductsWithFilter(@RequestBody Specification<Product> productSpecs);
	
	@GetMapping("/getProductById/{id}")
	Product getProductById(@PathVariable("id") Long id);
	
	@GetMapping("/getProductByTitle/{title}")
	Product getProductByTitle(@PathVariable("title") String title);
	
	@GetMapping("/getAllProductsByPage/")
	Page<Product> getAllProductsByPage(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize);
	

	@PostMapping("/getProductsWithPagingAndFiltering/")
	Page<Product> getProductsWithPagingAndFiltering(@RequestBody int pageNumber,
	                                                @RequestBody int pageSize,
	                                                @RequestBody Specification<Product> productSpecification);
	
	@GetMapping("/isProductWithTitleExists/{productTitle}")
	boolean isProductWithTitleExists(@PathVariable("productTitle") String productTitle);
	
	@PostMapping("/saveProduct")
	void saveProduct(@RequestBody Product product);
	
	@PostMapping("/addImage")
	void addImage(@RequestBody Product product,
	              @RequestBody MultipartFile file);
	
}
