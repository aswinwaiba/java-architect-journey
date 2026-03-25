package com.awaiba.products.service;

import com.awaiba.products.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService();
    }

    @Test
    void shouldAddProduct() {
        Product p = new Product("Test");
        productService.add(p);

        assertTrue(productExists(productService, "Test"));

    }

    @Test
    void shouldDeleteProduct() {
        Product p = new Product("Test");
        productService.add(p);

        boolean returnVal = productService.delete("Test");
        assertTrue(returnVal);

        assertFalse(productExists(productService, "Test"));
    }


    @Test
    void shouldReturnAllProducts() {
        Product p1 = new Product("Test1");
        productService.add(p1);

        Product p2 = new Product("Test2");
        productService.add(p2);

        assertEquals(2, productService.getAll().size());
    }

    @Test
    void shouldReturnFalseWhenDeletingNonExistentProduct() {
        boolean returnVal = productService.delete("NonExistentProduct");
        assertFalse(returnVal);
    }

    boolean productExists(ProductService productService, String productName) {
        return productService.getAll().stream().anyMatch(p -> p.productName().equals(productName));
    }

    @Test
    void shouldReturnEmptyListInitially() {
        assertEquals(0,productService.getAll().size());
    }
}