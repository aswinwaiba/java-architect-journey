package com.awaiba.products.repository;

import com.awaiba.products.model.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class InMemoryProductRepository implements ProductRepository{
    private final List<Product> store =  new ArrayList<>();

    @Override
    public List<Product> findAll() {
        return Collections.unmodifiableList(store);
    }

    @Override
    public void save(Product p) {
        store.add(p);
    }

    @Override
    public boolean deleteByName(String productName) {
        return store.removeIf(p -> p.productName().equals(productName));
    }
}
