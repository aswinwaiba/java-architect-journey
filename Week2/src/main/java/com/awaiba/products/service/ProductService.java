package com.awaiba.products.service;

import com.awaiba.products.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ProductService {
    private final List<Product> productList = new ArrayList<>();

    public List<Product> getAll() { return Collections.unmodifiableList(productList); }

    public void add(Product p) throws IllegalArgumentException{
        //TODO: Analyse What to do in case of duplicate names
        if(p == null) throw new IllegalArgumentException("Null value passed");
        if(p.productName().isEmpty() || p.productName().isBlank()) throw new IllegalArgumentException("Empty or blank name");
        productList.add(p);
    }

    public boolean delete(String productName) throws IllegalArgumentException{
        if(productName == null) throw new IllegalArgumentException("Null value passed");
        if(productName.isBlank()) throw new IllegalArgumentException("Empty or blank name");

        return productList.removeIf( p -> p.productName().equals(productName));
    }
}

