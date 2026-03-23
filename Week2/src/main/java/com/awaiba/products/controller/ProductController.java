package com.awaiba.products.controller;

import com.awaiba.products.service.ProductService;
import com.awaiba.products.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService service) {
        productService = service;
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAll(){
        return ResponseEntity.ok(productService.getAll());
    }

    @PostMapping("/products")
    public ResponseEntity<Void> add(@RequestBody Product p) {
        productService.add(p);

        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/products/{productName}")
    public ResponseEntity<Void> delete(@PathVariable String productName) {
        boolean status = productService.delete(productName);
        return status ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }
}
