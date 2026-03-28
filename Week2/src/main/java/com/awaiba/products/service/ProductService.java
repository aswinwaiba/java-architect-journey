package com.awaiba.products.service;

import com.awaiba.products.model.Product;
import com.awaiba.products.repository.InMemoryProductRepository;
import com.awaiba.products.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public List<Product> getAll() { return productRepository.findAll(); }

    public void add(Product p) throws IllegalArgumentException{
        //TODO: Analyse What to do in case of duplicate names
        if(p == null) throw new IllegalArgumentException("Null value passed");
        if(p.productName().isBlank()) throw new IllegalArgumentException("Empty or blank name");
        productRepository.save(p);
    }

    public boolean delete(String productName) throws IllegalArgumentException{
        if(productName == null) throw new IllegalArgumentException("Null value passed");
        if(productName.isBlank()) throw new IllegalArgumentException("Empty or blank name");

        return productRepository.deleteByName(productName);
    }
}

