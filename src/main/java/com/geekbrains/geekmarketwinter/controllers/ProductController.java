package com.geekbrains.geekmarketwinter.controllers;

import com.geekbrains.geekmarketwinter.entities.Product;
import com.geekbrains.geekmarketwinter.services.CategoryService;
import com.geekbrains.geekmarketwinter.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final Logger logger = LoggerFactory.getLogger(AdminController.class);
    
    private ProductService productService;
    private CategoryService categoryService;
    
    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    
    @GetMapping("/add")
    public String processAddProduct(Model model) {
        model.addAttribute("newProduct", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "add-product-form";
    }
    
    @PostMapping("/add")
    public String processAddProduct(Model model,
                                    @Valid @ModelAttribute("newProduct") Product newProduct,
                                    BindingResult theBindingResult,
                                    @RequestParam("file") MultipartFile file) {
        String newProductTitle = newProduct.getTitle();
        logger.debug("Processing addProduct form for: " + newProductTitle);
        if (theBindingResult.hasErrors()) {
            return "add-product-form";
        }
        Product existing = productService.getProductByTitle(newProductTitle);
        if(existing != null) {
            model.addAttribute("newProduct", newProduct);
            model.addAttribute("addProductError", "Product with current title already exists");
            logger.debug("Product title already exists.");
            return "add-product-form";
        }

        productService.addImage(newProduct, file);
        
        productService.saveProduct(newProduct);
        model.addAttribute("addProductSuccess", "Product successfully added");
        model.addAttribute("newProduct", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        logger.info("Successfully added product: " + newProductTitle);
        return "add-product-form";
    }

}
