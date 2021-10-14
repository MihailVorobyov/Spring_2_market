package com.geekbrains.geekmarketwinter.controllers;

import com.geekbrains.geekmarketwinter.entites.Product;
import com.geekbrains.geekmarketwinter.services.CategoryService;
import com.geekbrains.geekmarketwinter.services.ProductService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    
    @PostMapping("/add")
    public String processAddProduct(Model model,
                                    @Valid @ModelAttribute("newProduct") Product newProduct,
                                    BindingResult theBindingResult) {
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
        productService.saveProduct(newProduct);
        model.addAttribute("addProductSuccess", "Product successfully added");
        model.addAttribute("newProduct", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        logger.info("Successfully added product: " + newProductTitle);
        return "add-product-form";
    }
    
    @GetMapping("/addImage")
    public String addImage (Model model,
                            @ModelAttribute("newProduct") Product newProduct) {
        
        
        return "add-product-form";
    }
}
