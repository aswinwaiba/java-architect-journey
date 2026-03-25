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

    public void add(Product p) { productList.add(p); }

    public boolean delete(String productName) {
        return productList.removeIf( p -> p.productName().equals(productName));
    }
}

