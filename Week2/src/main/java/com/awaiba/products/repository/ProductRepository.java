package com.awaiba.products.repository;

import com.awaiba.products.model.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll();
    void save(Product p);
    boolean deleteByName(String productName);
}
