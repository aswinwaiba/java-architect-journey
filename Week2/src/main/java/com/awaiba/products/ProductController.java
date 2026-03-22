package com.awaiba.products;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService service) {
        productService = service;
    }

    @GetMapping("/products")
    public List<Product> getAll() { return productService.getAll(); }

    @PostMapping("/products")
    public void add(@RequestBody Product p) { productService.add(p); }

    @DeleteMapping("/products/{productName}")
    public boolean delete(@PathVariable String productName) {
        return productService.delete(productName);
    }
}
